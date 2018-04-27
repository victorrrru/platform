package com.fww;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 范文武
 * @date 2018/04/27 15:45
 */
public class HtmlUtil {
    private static final String REG_EX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    private static final String REG_EX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    private static final String REG_EX_HTML = "<[^>]+>";
    private static final String REG_EX_SPACE = "\\s*|\t|\r|\n";

    public HtmlUtil() {
    }

    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile("<script[^>]*?>[\\s\\S]*?<\\/script>", 2);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        Pattern p_style = Pattern.compile("<style[^>]*?>[\\s\\S]*?<\\/style>", 2);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        Pattern p_html = Pattern.compile("<[^>]+>", 2);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        Pattern p_space = Pattern.compile("\\s*|\t|\r|\n", 2);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");
        return htmlStr.trim();
    }

    public static String getTextFromHtml(String htmlStr) {
        if(StringUtils.isBlank(htmlStr)) {
            return "";
        } else {
            htmlStr = delHTMLTag(htmlStr);
            htmlStr = htmlStr.replaceAll("&nbsp;", "");
            return htmlStr;
        }
    }
}
