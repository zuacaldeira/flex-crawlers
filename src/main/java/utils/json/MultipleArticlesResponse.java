/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.json;

import java.util.List;

/**
 *
 * @author zua
 */
public class MultipleArticlesResponse {
    
    private String status;
    private String source;
    private String sortBy;
    private List<SingleArticleResponse> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<SingleArticleResponse> getArticles() {
        return articles;
    }

    public void setArticles(List<SingleArticleResponse> articles) {
        this.articles = articles;
    }

    
    
}
