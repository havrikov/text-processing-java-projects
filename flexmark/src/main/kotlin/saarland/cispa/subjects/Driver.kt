package saarland.cispa.subjects

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension
import com.vladsch.flexmark.ext.aside.AsideExtension
import com.vladsch.flexmark.ext.attributes.AttributesExtension
import com.vladsch.flexmark.ext.definition.DefinitionExtension
import com.vladsch.flexmark.ext.emoji.EmojiExtension
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension
import com.vladsch.flexmark.ext.gfm.issues.GfmIssuesExtension
import com.vladsch.flexmark.ext.gfm.strikethrough.SubscriptExtension
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension
import com.vladsch.flexmark.ext.gfm.users.GfmUsersExtension
import com.vladsch.flexmark.ext.ins.InsExtension
import com.vladsch.flexmark.ext.jekyll.front.matter.JekyllFrontMatterExtension
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.ext.toc.SimTocExtension
import com.vladsch.flexmark.ext.toc.TocExtension
import com.vladsch.flexmark.ext.typographic.TypographicExtension
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension
import com.vladsch.flexmark.ext.xwiki.macros.MacroExtension
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension
import com.vladsch.flexmark.ext.youtube.embedded.YouTubeLinkExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.jira.converter.JiraConverterExtension
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.parser.ParserEmulationProfile
import com.vladsch.flexmark.superscript.SuperscriptExtension
import com.vladsch.flexmark.util.options.MutableDataSet
import com.vladsch.flexmark.youtrack.converter.YouTrackConverterExtension

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.vladsch.flexmark"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        val docs = parsers.map { it.parse(text) }
        for (doc in docs) {
            renderers.forEach { it.render(doc) }
        }
    }

    private val parserExtensions = listOf(
        AsideExtension.create(),
        AbbreviationExtension.create(),
        AnchorLinkExtension.create(),
        SubscriptExtension.create(),
        SimTocExtension.create(),
        InsExtension.create(),
        WikiLinkExtension.create(),
//        StrikethroughSubscriptExtension.create(),
        AttributesExtension.create(),
        MacroExtension.create(),
        EnumeratedReferenceExtension.create(),
//        StrikethroughExtension.create(),
//        AutolinkExtension.create(),
        JekyllFrontMatterExtension.create(),
        TypographicExtension.create(),
        EmojiExtension.create(),
        YouTubeLinkExtension.create(),
        YamlFrontMatterExtension.create(),
        TocExtension.create(),
        TablesExtension.create(),
        JiraConverterExtension.create(),
        YouTrackConverterExtension.create(),
        DefinitionExtension.create(),
        FootnoteExtension.create(),
        JekyllTagExtension.create(),
        TaskListExtension.create(),
        GfmUsersExtension.create(),
        SuperscriptExtension.create(),
        EscapedCharacterExtension.create(),
        GfmIssuesExtension.create()
    )

    private val rendererExtenstions = listOf(
        AsideExtension.create(),
        AbbreviationExtension.create(),
        AnchorLinkExtension.create(),
        SubscriptExtension.create(),
        SimTocExtension.create(),
        InsExtension.create(),
        WikiLinkExtension.create(),
//        StrikethroughSubscriptExtension.create(),
        AttributesExtension.create(),
        MacroExtension.create(),
        EnumeratedReferenceExtension.create(),
//        StrikethroughExtension.create(),
        JekyllFrontMatterExtension.create(),
        TypographicExtension.create(),
        EmojiExtension.create(),
        YouTubeLinkExtension.create(),
        TocExtension.create(),
//        JiraConverterExtension.create(),
//        YouTrackConverterExtension.create(),
        DefinitionExtension.create(),
        FootnoteExtension.create(),
        JekyllTagExtension.create(),
        TaskListExtension.create(),
        GfmUsersExtension.create(),
        SuperscriptExtension.create(),
        EscapedCharacterExtension.create(),
        GfmIssuesExtension.create(),
        TablesExtension.create()
    )

    private val parsers: MutableList<Parser> = mutableListOf()
    private val renderers: MutableList<HtmlRenderer> = mutableListOf()

    init {
        // for each emulation profile create parsers and renderers with all extensions
        ParserEmulationProfile.values().forEach {
            val profile = MutableDataSet()
            profile.setFrom(it)

            val parserOptions = MutableDataSet(profile)
            parserOptions.set(Parser.EXTENSIONS, parserExtensions)
            parsers.add(Parser.builder(parserOptions).build())

            val rendererOptions = MutableDataSet(profile)
            rendererOptions.set(Parser.EXTENSIONS, rendererExtenstions)
            renderers.add(HtmlRenderer.builder(rendererOptions).build())
        }
    }


}
