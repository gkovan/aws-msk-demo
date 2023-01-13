
package com.gk.aws.msk.demo.pipe.model.twitter;

import java.util.HashMap;
import java.util.List;
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
    "id",
    "edit_history_tweet_ids",
    "text",
    "created_at"
})
@Generated("jsonschema2pojo")
public class Datum {

    @JsonProperty("id")
    private String id;
    @JsonProperty("edit_history_tweet_ids")
    private List<String> editHistoryTweetIds = null;
    @JsonProperty("text")
    private String text;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("edit_history_tweet_ids")
    public List<String> getEditHistoryTweetIds() {
        return editHistoryTweetIds;
    }

    @JsonProperty("edit_history_tweet_ids")
    public void setEditHistoryTweetIds(List<String> editHistoryTweetIds) {
        this.editHistoryTweetIds = editHistoryTweetIds;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
