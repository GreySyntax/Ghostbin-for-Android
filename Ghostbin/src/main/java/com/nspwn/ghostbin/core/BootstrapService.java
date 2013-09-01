
package com.nspwn.ghostbin.core;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.nspwn.ghostbin.core.beans.LanguageGroup;

import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.nspwn.ghostbin.core.Constants.Http.URL_LANGUAGES;
import static com.nspwn.ghostbin.core.Constants.Cache.LANGUAGE_CACHE;

/**
 * Bootstrap API service
 */
public class BootstrapService {

    private static final String TAG = "com.nspwn.ghostbin.core.BootstrapService";
    private UserAgentProvider userAgentProvider;

    /**
     * GSON instance to use for all request  with date format set up for proper parsing.
     */
    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    /**
     * Read and connect timeout in milliseconds
     */
    private static final int TIMEOUT = 30 * 1000;

    private static class JsonException extends IOException {

        private static final long serialVersionUID = 3774706606129390273L;

        /**
         * Create exception from {@link JsonParseException}
         *
         * @param cause
         */
        public JsonException(JsonParseException cause) {
            super(cause.getMessage());
            initCause(cause);
        }
    }

    /**
     * Create bootstrap service
     *
     * @param userAgentProvider
     */
    public BootstrapService(final UserAgentProvider userAgentProvider) {
        this.userAgentProvider = userAgentProvider;
    }

    /**
     * Execute request
     *
     * @param request
     * @return request
     * @throws IOException
     */
    protected HttpRequest execute(HttpRequest request) throws IOException {
        if (!configure(request).ok())
            throw new IOException("Unexpected response code: " + request.code());
        return request;
    }

    private HttpRequest configure(final HttpRequest request) {
        request.connectTimeout(TIMEOUT).readTimeout(TIMEOUT);
        request.userAgent(userAgentProvider.get());

        if(isPostOrPut(request))
            request.contentType(Constants.Http.CONTENT_TYPE_JSON); // All PUT & POST requests to Parse.com api must be in JSON - https://www.parse.com/docs/rest#general-requests

        return request;
    }

    private boolean isPostOrPut(HttpRequest request) {
        return request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_POST)
               || request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_PUT);

    }

    private <V> V fromJson(HttpRequest request, Class<V> target) throws IOException {
        Reader reader = request.bufferedReader();
        try {
            return GSON.fromJson(reader, target);
        } catch (JsonParseException e) {
            throw new JsonException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
                // Ignored
            }
        }
    }

    private long getRemoteLastModified() {
        try {
            HttpRequest request = execute(HttpRequest.head(URL_LANGUAGES));
            return request.lastModified();
        } catch (IOException e) {
            Log.e(TAG, "IO Error...", e);
        }

        return new Date().getTime();
    }

    private LanguageGroup[] fetchCache(File cacheFile) {
        if (cacheFile.exists() && cacheFile.canRead()) {
            // Has the remote file changed?
            if (this.getRemoteLastModified() > cacheFile.lastModified())
                return null;
            try {
                // Load the cached data
                InputStream inputStream = new FileInputStream(cacheFile);
                byte[] data = new byte[inputStream.available()];
                inputStream.read(data);
                inputStream.close();

                // Parse it to JSON
                return GSON.fromJson(new String(data), LanguageGroup[].class);
            } catch (FileNotFoundException fnf) {
                Log.e(TAG, "Languages file not found error", fnf);
            } catch (IOException io) {
                Log.e(TAG, "IO Error", io);
            }
        }

        return null;
    }

    public List<LanguageGroup> getLanguages(File cacheDir) throws IOException {
        try {
            File cacheFile = new File(cacheDir, LANGUAGE_CACHE);
            LanguageGroup[] response = this.fetchCache(cacheFile);
            boolean fromCache = (response != null);
            long lastModified = cacheFile.lastModified();

            if (! fromCache) {
                HttpRequest request = execute(HttpRequest.get(URL_LANGUAGES));
                lastModified = request.lastModified();
                response = fromJson(request, LanguageGroup[].class);
            }

            if (response != null) {
                if (! fromCache) {
                    // Write response to cache file
                    FileOutputStream fileOutputStream = new FileOutputStream(cacheFile, false);
                    fileOutputStream.write(GSON.toJson(response).getBytes());
                    fileOutputStream.close();

                    // Update the last modified time
                    cacheFile.setLastModified(lastModified);
                }
                return Arrays.asList(response);
            }

            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }
}
