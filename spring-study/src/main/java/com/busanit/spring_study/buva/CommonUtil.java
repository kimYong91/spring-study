package com.busanit.spring_study.buva;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
    // 마크다운을 => html로 변환
    public String markdownToHtml(String markdown) {
        // import org.commonmark.parser.Parser;
        Parser parser = Parser.builder().build();
        // import org.commonmark.node.Node;
        Node document = parser.parse(markdown);
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
        return htmlRenderer.render(document);
    }
}
