package com.gk.aws.msk.demo.pipe.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gk.aws.msk.demo.pipe.model.twitter.TwitterResponse;
import com.gk.aws.msk.demo.pipe.task.JsonTask;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Service
public class TwitterService {

    private JsonTask jsonTask;

    @Autowired
    public TwitterService(JsonTask jsonTask) {
        this.jsonTask = jsonTask;
    }

    public TwitterResponse getTweets() {
        String bearerToken = System.getenv("BEARER_TOKEN");
        String twitterResponseString = null;
        try {
            twitterResponseString = getTweets("1976143068", bearerToken);
            System.out.println(twitterResponseString);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return jsonTask.convertTwitterResponseStringToObject(twitterResponseString);
    }

      /*
   * This method calls the v2 User Tweet timeline endpoint by user ID
   * */
  private String getTweets(String userId, String bearerToken) throws IOException, URISyntaxException {
    String tweetResponse = null;

    HttpClient httpClient = HttpClients.custom()
        .setDefaultRequestConfig(RequestConfig.custom()
            .setCookieSpec(CookieSpecs.STANDARD).build())
        .build();

    URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/tweets", userId));
    ArrayList<NameValuePair> queryParameters;
    queryParameters = new ArrayList<>();
    queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
    uriBuilder.addParameters(queryParameters);

    HttpGet httpGet = new HttpGet(uriBuilder.build());
    httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
    httpGet.setHeader("Content-Type", "application/json");

    HttpResponse response = httpClient.execute(httpGet);
    HttpEntity entity = response.getEntity();
    if (null != entity) {
      tweetResponse = EntityUtils.toString(entity, "UTF-8");
    }
    return tweetResponse;
  }
    
}
