package com.example.assistant.assitance.com.example.assitance.util;

import java.util.Comparator;

/**
 * 拼音的比较器
 */
public class FirstWordCompare implements Comparator<ContactInfo> {
    @Override
    public int compare(ContactInfo lhs, ContactInfo rhs) {
        return lhs.getFirstWord().compareTo(rhs.getFirstWord());
    }
}
