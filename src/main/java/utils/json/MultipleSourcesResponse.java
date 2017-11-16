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
public class MultipleSourcesResponse {
    private String status;
    private List<SingleSourceResponse> sources;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SingleSourceResponse> getSources() {
        return sources;
    }

    public void setSources(List<SingleSourceResponse> sources) {
        this.sources = sources;
    }

}
