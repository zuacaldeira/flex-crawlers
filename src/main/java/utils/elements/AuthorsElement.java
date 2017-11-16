/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.elements;

import java.util.HashSet;
import java.util.Set;
import utils.NamesUtils;

/**
 * An author element stores a single string value with all the authors names and
 * provides a mean to extract a set of string, one for each author.
 *
 * @author <a href="emailto">zuacaldeiragmail.com</a>
 */
public class AuthorsElement extends ArticleElement {

    /**
     * Initializes a new author element with the given value.
     *
     * @param value A comma-separated string with all authors names.
     */
    public AuthorsElement(String value) {
        super(value);
    }

    /**
     * Converts the single name string into a set of names.
     *
     * @return Return the set with names contained in the single name string. If
     * the name string is null or empty, returns an empty set.
     */
    public Set<String> getAuthors() {
        if (getValue() != null && !getValue().isEmpty()) {
            return NamesUtils.getInstance().extractAuthorsNames(getValue());
        }
        return new HashSet<>();
    }

}
