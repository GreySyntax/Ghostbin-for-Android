

package com.nspwn.ghostbin.core;

/**
 * Bootstrap constants
 */
public class Constants {

    public static class Auth {
        private Auth() {}

        /**
         * Account type id
         */
        public static final String BOOTSTRAP_ACCOUNT_TYPE = "com.nspwn.ghostbin";

        /**
         * Account name
         */
        public static final String BOOTSTRAP_ACCOUNT_NAME = "Ghostbin";

        /**
         * Provider id
         */
        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "com.nspwn.ghostbin.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = BOOTSTRAP_ACCOUNT_TYPE;
    }

    public static class Cache {
        public static final String LANGUAGE_CACHE = "languages.json";
    }
    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for bootstrap!
     */
    public static class Http {
        private Http() {}



        /**
         * Base URL for all requests
         */
        public static final String URL_BASE = "https://ghostbin.com";

        public static final String URL_LANGUAGES = URL_BASE + "/languages.json";

//        /**
//         * Authentication URL
//         */
//        public static final String URL_AUTH = URL_BASE + "/1/login";
//
//        /**
//         * List Users URL
//         */
//        public static final String URL_USERS = URL_BASE + "/1/users";
//
//        /**
//         * List News URL
//         */
//        public static final String URL_NEWS = URL_BASE + "/1/classes/News";
//
//        /**
//         * List Checkin's URL
//         */
//        public static final String URL_CHECKINS = URL_BASE + "/1/classes/Locations";

//        public static final String PARSE_APP_ID = "zHb2bVia6kgilYRWWdmTiEJooYA17NnkBSUVsr4H";
//        public static final String PARSE_REST_API_KEY = "N2kCY1T3t3Jfhf9zpJ5MCURn3b25UpACILhnf5u9";
//        public static final String HEADER_PARSE_REST_API_KEY = "X-Parse-REST-API-Key";
//        public static final String HEADER_PARSE_APP_ID = "X-Parse-Application-Id";
        public static final String CONTENT_TYPE_JSON = "application/json";
//        public static final String USERNAME = "username";
//        public static final String PASSWORD = "password";
//        public static final String SESSION_TOKEN = "sessionToken";


    }

    public static class Intent {
        private Intent() {}

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "com.nspwn.ghostbin.";

    }

    public static class Notification{
        private Notification() {}

        public static final int TIMER_NOTIFICATION_ID = 1000; // Why 1000? Why not? :)
    }

}


