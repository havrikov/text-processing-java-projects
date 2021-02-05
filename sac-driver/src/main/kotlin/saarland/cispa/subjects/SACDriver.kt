package saarland.cispa.subjects

import org.w3c.css.sac.DocumentHandler
import org.w3c.css.sac.InputSource
import org.w3c.css.sac.LexicalUnit
import org.w3c.css.sac.Parser
import org.w3c.css.sac.SACMediaList
import org.w3c.css.sac.SelectorList

abstract class SACDriver : SubjectExecutor() {
    protected abstract val parser: Parser

    override fun processInput(text: String) {
        parser.setDocumentHandler(object : DocumentHandler {
            override fun namespaceDeclaration(prefix: String?, uri: String?) = Unit
            override fun endMedia(media: SACMediaList?) = Unit
            override fun startSelector(selectors: SelectorList?) = Unit
            override fun property(name: String?, value: LexicalUnit?, important: Boolean) = Unit
            override fun ignorableAtRule(atRule: String?) = Unit
            override fun endPage(name: String?, pseudo_page: String?) = Unit
            override fun endDocument(source: InputSource?) = Unit
            override fun startPage(name: String?, pseudo_page: String?) = Unit
            override fun endFontFace() = Unit
            override fun startMedia(media: SACMediaList?) = Unit
            override fun endSelector(selectors: SelectorList?) = Unit
            override fun importStyle(uri: String?, media: SACMediaList?, defaultNamespaceURI: String?) = Unit
            override fun comment(text: String?) = Unit
            override fun startDocument(source: InputSource?) = Unit
            override fun startFontFace() = Unit
        })
        parser.parseStyleSheet(text)
    }
}
