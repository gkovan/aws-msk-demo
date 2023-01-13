
package com.gk.aws.msk.demo.pipe.model.twitter;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "next_token",
    "result_count",
    "newest_id",
    "oldest_id"
})
@Generated("jsonschema2pojo")
public class Meta {

    @JsonProperty("next_token")
    private String nextToken;
    @JsonProperty("result_count")
    private Integer resultCount;
    @JsonProperty("newest_id")
    private String newestId;
    @JsonProperty("oldest_id")
    private String oldestId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("next_token")
    public String getNextToken() {
        return nextToken;
    }

    @JsonProperty("next_token")
    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    @JsonProperty("result_count")
    public Integer getResultCount() {
        return resultCount;
    }

    @JsonProperty("result_count")
    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    @JsonProperty("newest_id")
    public String getNewestId() {
        return newestId;
    }

    @JsonProperty("newest_id")
    public void setNewestId(String newestId) {
        this.newestId = newestId;
    }

    @JsonProperty("oldest_id")
    public String getOldestId() {
        return oldestId;
    }

    @JsonProperty("oldest_id")
    public void setOldestId(String oldestId) {
        this.oldestId = oldestId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
