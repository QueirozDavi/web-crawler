package com.project.webcrawler.util;

import java.util.regex.Pattern;

public class PatternUtils {

    public static Pattern excludePattern() {
        return Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
    }
}
