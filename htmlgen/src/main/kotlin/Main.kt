import com.github.houbb.markdown.toc.core.impl.AtxMarkdownToc
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension
import org.commonmark.node.Heading
import org.commonmark.node.Link
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.AttributeProvider
import org.commonmark.renderer.html.HtmlRenderer
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.notExists
import kotlin.io.path.readText

val MENU = arrayListOf<MenuNode>(
    markdownMenuNode("overview.md", false),
    markdownMenuNode("oauth.md", false),
    markdownMenuNode("api-response.md", false),
    markdownMenuNode("request-setting.md", false),
    MenuNode(""),
    markdownMenuNode("archive/index.md"),
    markdownMenuNode("archive/business-model.md", false),
    markdownMenuNode("archive/accurate-search-game.md"),
    markdownMenuNode("archive/search-game-list.md"),
    markdownMenuNode("archive/find-game.md"),
    markdownMenuNode("archive/find-org.md"),
    markdownMenuNode("archive/find-character.md"),
    markdownMenuNode("archive/find-person.md"),
    markdownMenuNode("archive/find-org-game.md"),
    markdownMenuNode("archive/by-release-date.md"),
    markdownMenuNode("archive/random-game.md"),
    MenuNode(""),
    markdownMenuNode("change-list.md"),
)

val projectPath: Path = Paths.get("").toAbsolutePath()
val developerPath: Path = projectPath.resolve("developers")
val targetPath: Path = projectPath.resolve("htmlgen/src/main/resources")
val resultHTMLFile: Path = targetPath.resolve("developer.html")
val templateHTML = targetPath.resolve("template.html").readText()

data class MenuNode(
    var file: String,
    var link: String? = null,
    val simpleHeadingAnchor: Boolean = true
) {
    val name: String

    init {
        val split = file.split("/")
        name = split[split.size - 1].replace(".md", "")
    }

    fun getPath(): Path = developerPath.resolve(file)
}


fun markdownMenuNode(md: String, simpleHeadingAnchor: Boolean = true): MenuNode {
    return MenuNode(file = md, simpleHeadingAnchor = simpleHeadingAnchor);
}

fun main(args: Array<String>) {
    if (resultHTMLFile.notExists()) Files.createFile(resultHTMLFile)

    val nav: MutableList<String> = arrayListOf()

    val mainHTML = MENU.map { menu ->

        //判定在菜单里插些额外数据
        if (menu.file.isBlank()) {
            nav.add("---")
            return@map ""
        }

        val path = menu.getPath()

        val tocList = AtxMarkdownToc.newInstance()
            .write(false)
            .genTocFile(path.toString())
            .tocLines.filter { !it.startsWith("# Table of Contents") }

        nav.addAll(
            if (menu.simpleHeadingAnchor) listOf(tocList[0]) else tocList
        )

        markdownToHtml(path.readText(), menu.simpleHeadingAnchor)
    }.filter { it.isNotBlank() }.joinToString("<hr/>")

    // 菜单一次性拼接，如有收缩的需要，可以改成分批拼
    val aside = nav.filter { it.isNotBlank() }
        .filter { !it.startsWith("    *") }
        .fold(StringBuilder()) { str, n -> str.appendLine(n) }
    println(aside)

    val asideHTML = markdownToHtml(aside.toString(), false)

    //token replace
    Files.writeString(
        resultHTMLFile,
        templateHTML.replaceFirst("{{aside}}", asideHTML).replaceFirst("{{main}}", mainHTML)
    )

}


fun markdownToHtml(markdown: String, simpleHeadingAnchor: Boolean): String {
    //h标题生成id
    val headingAnchorExtensions = setOf(HeadingAnchorExtension.create())
    //转换table的HTML
    val tableExtension = listOf(TablesExtension.create())

    val parser = Parser.builder()
        .extensions(tableExtension)
        .build()

    val document = parser.parse(markdown)
    val renderer = HtmlRenderer.builder()
        .sanitizeUrls(false)
//        .escapeHtml(false)
        .extensions(headingAnchorExtensions)
        .extensions(tableExtension)
        .attributeProviderFactory { CustomAttributeProvider(simpleHeadingAnchor) }
        .build()

    return renderer.render(document)
}

class CustomAttributeProvider(private val simpleHeadingAnchor: Boolean) : AttributeProvider {

    override fun setAttributes(node: Node, tagName: String, attributes: MutableMap<String, String>) {
        if (node is Link) {
            val destination = node.destination

            //改变a标签的target为新标签页打开
            if (destination.startsWith("https://") || destination.startsWith("http://")) {
                attributes["target"] = "_blank"
                return
            }

            //TODO 这块暂时先这样，懒得写了，有需要再说
            if (destination.endsWith(".md")) {
                node.destination = destination.replace(".md", "")
                attributes["href"] = node.destination
                attributes["href"] = "javascript:void(0)"
            }

        }

        //去除单个页面中除大标题以外的所有标题导航
        if (node is Heading && simpleHeadingAnchor) {
            if (node.level > 1) attributes["id"] = ""
        }
    }
}