package ais.koutroulis.gr.test;

/**
 * Created by c0nfr0ntier on 12/7/2016.
 */
public class JsonResponseProvider {
    private static String ais0058GrantTokenJsonString = "{\n" +
            "  \"token\": \"grantAccessToken\"\n" +
            "}";
    private static String ais0058DeniedTokenJsonString = "{\n" +
            "  \"error\": \"Invalid login, please try again\",\n" +
            "  \"stacktrace\": null,\n" +
            "  \"debuginfo\": null,\n" +
            "  \"reproductionlink\": null\n" +
            "}";
    private static String ais0058UserDetailsJsonString = "  {\n" +
            "    \"id\": 5,\n" +
            "    \"username\": \"ais0058\",\n" +
            "    \"fullname\": \"Ioannis Antonatos\",\n" +
            "    \"email\": \"antonatos@hotmail.com\",\n" +
            "    \"department\": \"\",\n" +
            "    \"firstaccess\": 1467307347,\n" +
            "    \"lastaccess\": 1468219321,\n" +
            "    \"description\": \"\",\n" +
            "    \"descriptionformat\": 1,\n" +
            "    \"city\": \"Athens\",\n" +
            "    \"country\": \"GR\",\n" +
            "    \"profileimageurlsmall\": \"http://ais-temp.daidalos.teipir.gr/moodle/theme/image.php/clean/core/1467270948/u/f2\",\n" +
            "    \"profileimageurl\": \"http://ais-temp.daidalos.teipir.gr/moodle/theme/image.php/clean/core/1467270948/u/f1\",\n" +
            "    \"preferences\": [\n" +
            "      {\n" +
            "        \"name\": \"auth_forcepasswordchange\",\n" +
            "        \"value\": \"0\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"email_bounce_count\",\n" +
            "        \"value\": \"1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"email_send_count\",\n" +
            "        \"value\": \"3\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"login_failed_count_since_success\",\n" +
            "        \"value\": \"1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"_lastloaded\",\n" +
            "        \"value\": 1468221629\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n";
    private static String ais0058UnreadMessagesJsonString = "{\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"useridfrom\": 3,\n" +
            "      \"useridto\": 5,\n" +
            "      \"subject\": \"New message from Christodoulos Koutroulis\",\n" +
            "      \"text\": \"<p>&Icirc;&pound;&Icirc;&iquest;&Iuml;&#133; &Iuml;&#131;&Iuml;&#132;&Icirc;&shy;&Icirc;&raquo;&Icirc;&frac12;&Iuml;&#137; &Icirc;&frac34;&Icirc;&plusmn;&Icirc;&frac12;&Icirc;&not;</p>\",\n" +
            "      \"fullmessage\": \"Σου στέλνω ξανά\\n\\n---------------------------------------------------------------------\\nThis is a copy of a message sent to you at \\\"MSc AIS\\\". Go to http://ais-temp.daidalos.teipir.gr/moodle/message/index.php?user=5&id=3 to reply.\",\n" +
            "      \"fullmessageformat\": 0,\n" +
            "      \"fullmessagehtml\": \"\",\n" +
            "      \"smallmessage\": \"Σου στέλνω ξανά\",\n" +
            "      \"notification\": 0,\n" +
            "      \"contexturl\": null,\n" +
            "      \"contexturlname\": null,\n" +
            "      \"timecreated\": 1467568241,\n" +
            "      \"timeread\": 0,\n" +
            "      \"usertofullname\": \"Ioannis Antonatos\",\n" +
            "      \"userfromfullname\": \"Christodoulos Koutroulis\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"useridfrom\": 3,\n" +
            "      \"useridto\": 5,\n" +
            "      \"subject\": \"New message from Christodoulos Koutroulis\",\n" +
            "      \"text\": \"<p>&Icirc;&nbsp;&Icirc;&not;&Iuml;&#129;&Icirc;&micro; &Icirc;&ordm;&Icirc;&sup1; &Icirc;&micro;&Iuml;&#131;&Iuml;&#141; &Icirc;&shy;&Icirc;&frac12;&Icirc;&plusmn; &Icirc;&frac14;&Icirc;&reg;&Icirc;&frac12;&Iuml;&#133;&Icirc;&frac14;&Icirc;&plusmn;.</p>\",\n" +
            "      \"fullmessage\": \"Πάρε κι εσύ ένα μήνυμα.\\n\\n---------------------------------------------------------------------\\nThis is a copy of a message sent to you at \\\"MSc AIS\\\". Go to http://ais-temp.daidalos.teipir.gr/moodle/message/index.php?user=5&id=3 to reply.\",\n" +
            "      \"fullmessageformat\": 0,\n" +
            "      \"fullmessagehtml\": \"\",\n" +
            "      \"smallmessage\": \"Πάρε κι εσύ ένα μήνυμα.\",\n" +
            "      \"notification\": 0,\n" +
            "      \"contexturl\": null,\n" +
            "      \"contexturlname\": null,\n" +
            "      \"timecreated\": 1467567827,\n" +
            "      \"timeread\": 0,\n" +
            "      \"usertofullname\": \"Ioannis Antonatos\",\n" +
            "      \"userfromfullname\": \"Christodoulos Koutroulis\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"warnings\": []\n" +
            "}";

    private static String ais0058ReadMessagesJsonString = "{\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"useridfrom\": 3,\n" +
            "      \"useridto\": 5,\n" +
            "      \"subject\": \"New message from Christodoulos Koutroulis\",\n" +
            "      \"text\": \"<p>&Icirc;&pound;&Icirc;&iquest;&Iuml;&#133; &Iuml;&#131;&Iuml;&#132;&Icirc;&shy;&Icirc;&raquo;&Icirc;&frac12;&Iuml;&#137; &Icirc;&plusmn;&Icirc;&ordm;&Iuml;&#140;&Icirc;&frac14;&Icirc;&plusmn; &Icirc;&shy;&Icirc;&frac12;&Icirc;&plusmn; &Icirc;&sup3;&Icirc;&sup1;&Icirc;&plusmn; &Icirc;&frac12;&Icirc;&plusmn; &Icirc;&acute;&Icirc;&sup1;&Icirc;&plusmn;&Icirc;&sup2;&Icirc;&not;&Iuml;&#131;&Icirc;&micro;&Icirc;&sup1;&Iuml;&#130; &Icirc;&shy;&Icirc;&frac12;&Icirc;&plusmn; &Icirc;&plusmn;&Iuml;&#128;&Iuml;&#140; &Iuml;&#132;&Icirc;&plusmn; &Iuml;&#128;&Iuml;&#129;&Icirc;&iquest;&Icirc;&middot;&Icirc;&sup3;&Icirc;&iquest;&Iuml;&#141;&Icirc;&frac14;&Icirc;&micro;&Icirc;&frac12;&Icirc;&plusmn;.</p>\",\n" +
            "      \"fullmessage\": \"Σου στέλνω ακόμα ένα για να διαβάσεις ένα από τα προηγούμενα.\\n\\n---------------------------------------------------------------------\\nThis is a copy of a message sent to you at \\\"MSc AIS\\\". Go to http://ais-temp.daidalos.teipir.gr/moodle/message/index.php?user=5&id=3 to reply.\",\n" +
            "      \"fullmessageformat\": 0,\n" +
            "      \"fullmessagehtml\": \"\",\n" +
            "      \"smallmessage\": \"Σου στέλνω ακόμα ένα για να διαβάσεις ένα από τα προηγούμενα.\",\n" +
            "      \"notification\": 0,\n" +
            "      \"contexturl\": null,\n" +
            "      \"contexturlname\": null,\n" +
            "      \"timecreated\": 1468241947,\n" +
            "      \"timeread\": 1468241988,\n" +
            "      \"usertofullname\": \"Ioannis Antonatos\",\n" +
            "      \"userfromfullname\": \"Christodoulos Koutroulis\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"useridfrom\": 3,\n" +
            "      \"useridto\": 5,\n" +
            "      \"subject\": \"New message from Christodoulos Koutroulis\",\n" +
            "      \"text\": \"<p>&Icirc;&pound;&Icirc;&iquest;&Iuml;&#133; &Iuml;&#131;&Iuml;&#132;&Icirc;&shy;&Icirc;&raquo;&Icirc;&frac12;&Iuml;&#137; &Icirc;&frac34;&Icirc;&plusmn;&Icirc;&frac12;&Icirc;&not;</p>\",\n" +
            "      \"fullmessage\": \"Σου στέλνω ξανά\\n\\n---------------------------------------------------------------------\\nThis is a copy of a message sent to you at \\\"MSc AIS\\\". Go to http://ais-temp.daidalos.teipir.gr/moodle/message/index.php?user=5&id=3 to reply.\",\n" +
            "      \"fullmessageformat\": 0,\n" +
            "      \"fullmessagehtml\": \"\",\n" +
            "      \"smallmessage\": \"Σου στέλνω ξανά\",\n" +
            "      \"notification\": 0,\n" +
            "      \"contexturl\": null,\n" +
            "      \"contexturlname\": null,\n" +
            "      \"timecreated\": 1467568241,\n" +
            "      \"timeread\": 1468241988,\n" +
            "      \"usertofullname\": \"Ioannis Antonatos\",\n" +
            "      \"userfromfullname\": \"Christodoulos Koutroulis\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"useridfrom\": 3,\n" +
            "      \"useridto\": 5,\n" +
            "      \"subject\": \"New message from Christodoulos Koutroulis\",\n" +
            "      \"text\": \"<p>&Icirc;&nbsp;&Icirc;&not;&Iuml;&#129;&Icirc;&micro; &Icirc;&ordm;&Icirc;&sup1; &Icirc;&micro;&Iuml;&#131;&Iuml;&#141; &Icirc;&shy;&Icirc;&frac12;&Icirc;&plusmn; &Icirc;&frac14;&Icirc;&reg;&Icirc;&frac12;&Iuml;&#133;&Icirc;&frac14;&Icirc;&plusmn;.</p>\",\n" +
            "      \"fullmessage\": \"Πάρε κι εσύ ένα μήνυμα.\\n\\n---------------------------------------------------------------------\\nThis is a copy of a message sent to you at \\\"MSc AIS\\\". Go to http://ais-temp.daidalos.teipir.gr/moodle/message/index.php?user=5&id=3 to reply.\",\n" +
            "      \"fullmessageformat\": 0,\n" +
            "      \"fullmessagehtml\": \"\",\n" +
            "      \"smallmessage\": \"Πάρε κι εσύ ένα μήνυμα.\",\n" +
            "      \"notification\": 0,\n" +
            "      \"contexturl\": null,\n" +
            "      \"contexturlname\": null,\n" +
            "      \"timecreated\": 1467567827,\n" +
            "      \"timeread\": 1468241987,\n" +
            "      \"usertofullname\": \"Ioannis Antonatos\",\n" +
            "      \"userfromfullname\": \"Christodoulos Koutroulis\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"warnings\": []\n" +
            "}";
    private static String fourCoursesAndTwoAssignmentsJsonString = "{\n" +
            "  \"courses\": [\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"fullname\": \"Βιομηχανική Πληροφορική\",\n" +
            "      \"shortname\": \"indcomp\",\n" +
            "      \"timemodified\": 1467304055,\n" +
            "      \"assignments\": [\n" +
            "        {\n" +
            "          \"id\": 2,\n" +
            "          \"cmid\": 6,\n" +
            "          \"course\": 2,\n" +
            "          \"name\": \"Εργασία για τα βιομηχανικά πρωτόκολλα.\",\n" +
            "          \"nosubmissions\": 0,\n" +
            "          \"submissiondrafts\": 0,\n" +
            "          \"sendnotifications\": 0,\n" +
            "          \"sendlatenotifications\": 0,\n" +
            "          \"sendstudentnotifications\": 1,\n" +
            "          \"duedate\": 1473886800,\n" +
            "          \"allowsubmissionsfromdate\": 1467925200,\n" +
            "          \"grade\": 100,\n" +
            "          \"timemodified\": 1467961291,\n" +
            "          \"completionsubmit\": 0,\n" +
            "          \"cutoffdate\": 1473922500,\n" +
            "          \"teamsubmission\": 0,\n" +
            "          \"requireallteammemberssubmit\": 0,\n" +
            "          \"teamsubmissiongroupingid\": 0,\n" +
            "          \"blindmarking\": 0,\n" +
            "          \"revealidentities\": 0,\n" +
            "          \"attemptreopenmethod\": \"none\",\n" +
            "          \"maxattempts\": -1,\n" +
            "          \"markingworkflow\": 0,\n" +
            "          \"markingallocation\": 0,\n" +
            "          \"requiresubmissionstatement\": 0,\n" +
            "          \"configs\": [\n" +
            "            {\n" +
            "              \"id\": 11,\n" +
            "              \"assignment\": 2,\n" +
            "              \"plugin\": \"onlinetext\",\n" +
            "              \"subtype\": \"assignsubmission\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 12,\n" +
            "              \"assignment\": 2,\n" +
            "              \"plugin\": \"file\",\n" +
            "              \"subtype\": \"assignsubmission\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 13,\n" +
            "              \"assignment\": 2,\n" +
            "              \"plugin\": \"file\",\n" +
            "              \"subtype\": \"assignsubmission\",\n" +
            "              \"name\": \"maxfilesubmissions\",\n" +
            "              \"value\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 14,\n" +
            "              \"assignment\": 2,\n" +
            "              \"plugin\": \"file\",\n" +
            "              \"subtype\": \"assignsubmission\",\n" +
            "              \"name\": \"maxsubmissionsizebytes\",\n" +
            "              \"value\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 15,\n" +
            "              \"assignment\": 2,\n" +
            "              \"plugin\": \"comments\",\n" +
            "              \"subtype\": \"assignsubmission\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 16,\n" +
            "              \"assignment\": 2,\n" +
            "              \"plugin\": \"comments\",\n" +
            "              \"subtype\": \"assignfeedback\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 17,\n" +
            "              \"assignment\": 2,\n" +
            "              \"plugin\": \"comments\",\n" +
            "              \"subtype\": \"assignfeedback\",\n" +
            "              \"name\": \"commentinline\",\n" +
            "              \"value\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 18,\n" +
            "              \"assignment\": 2,\n" +
            "              \"plugin\": \"editpdf\",\n" +
            "              \"subtype\": \"assignfeedback\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 19,\n" +
            "              \"assignment\": 2,\n" +
            "              \"plugin\": \"offline\",\n" +
            "              \"subtype\": \"assignfeedback\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 20,\n" +
            "              \"assignment\": 2,\n" +
            "              \"plugin\": \"file\",\n" +
            "              \"subtype\": \"assignfeedback\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"0\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"intro\": \"<p>Διαβάστε τα βιομηχανικά πρωτόκολλα επικοινωνίας και αναφέρετε περιληπτικά το καθένα από αυτά.</p>\",\n" +
            "          \"introformat\": 1\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"fullname\": \"Προηγμένα Θέματα Αντικειμενοστραφούς Προγραμματισμού\",\n" +
            "      \"shortname\": \"advprogr\",\n" +
            "      \"timemodified\": 1467303957,\n" +
            "      \"assignments\": [\n" +
            "        {\n" +
            "          \"id\": 1,\n" +
            "          \"cmid\": 5,\n" +
            "          \"course\": 3,\n" +
            "          \"name\": \"Εργασία 1η\",\n" +
            "          \"nosubmissions\": 0,\n" +
            "          \"submissiondrafts\": 0,\n" +
            "          \"sendnotifications\": 0,\n" +
            "          \"sendlatenotifications\": 0,\n" +
            "          \"sendstudentnotifications\": 1,\n" +
            "          \"duedate\": 1470776400,\n" +
            "          \"allowsubmissionsfromdate\": 1467493200,\n" +
            "          \"grade\": 100,\n" +
            "          \"timemodified\": 1467567633,\n" +
            "          \"completionsubmit\": 0,\n" +
            "          \"cutoffdate\": 1470850500,\n" +
            "          \"teamsubmission\": 0,\n" +
            "          \"requireallteammemberssubmit\": 0,\n" +
            "          \"teamsubmissiongroupingid\": 0,\n" +
            "          \"blindmarking\": 0,\n" +
            "          \"revealidentities\": 0,\n" +
            "          \"attemptreopenmethod\": \"none\",\n" +
            "          \"maxattempts\": -1,\n" +
            "          \"markingworkflow\": 0,\n" +
            "          \"markingallocation\": 0,\n" +
            "          \"requiresubmissionstatement\": 0,\n" +
            "          \"configs\": [\n" +
            "            {\n" +
            "              \"id\": 1,\n" +
            "              \"assignment\": 1,\n" +
            "              \"plugin\": \"onlinetext\",\n" +
            "              \"subtype\": \"assignsubmission\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 2,\n" +
            "              \"assignment\": 1,\n" +
            "              \"plugin\": \"file\",\n" +
            "              \"subtype\": \"assignsubmission\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 3,\n" +
            "              \"assignment\": 1,\n" +
            "              \"plugin\": \"file\",\n" +
            "              \"subtype\": \"assignsubmission\",\n" +
            "              \"name\": \"maxfilesubmissions\",\n" +
            "              \"value\": \"10\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 4,\n" +
            "              \"assignment\": 1,\n" +
            "              \"plugin\": \"file\",\n" +
            "              \"subtype\": \"assignsubmission\",\n" +
            "              \"name\": \"maxsubmissionsizebytes\",\n" +
            "              \"value\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 5,\n" +
            "              \"assignment\": 1,\n" +
            "              \"plugin\": \"comments\",\n" +
            "              \"subtype\": \"assignsubmission\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 6,\n" +
            "              \"assignment\": 1,\n" +
            "              \"plugin\": \"comments\",\n" +
            "              \"subtype\": \"assignfeedback\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 7,\n" +
            "              \"assignment\": 1,\n" +
            "              \"plugin\": \"comments\",\n" +
            "              \"subtype\": \"assignfeedback\",\n" +
            "              \"name\": \"commentinline\",\n" +
            "              \"value\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 8,\n" +
            "              \"assignment\": 1,\n" +
            "              \"plugin\": \"editpdf\",\n" +
            "              \"subtype\": \"assignfeedback\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 9,\n" +
            "              \"assignment\": 1,\n" +
            "              \"plugin\": \"offline\",\n" +
            "              \"subtype\": \"assignfeedback\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": 10,\n" +
            "              \"assignment\": 1,\n" +
            "              \"plugin\": \"file\",\n" +
            "              \"subtype\": \"assignfeedback\",\n" +
            "              \"name\": \"enabled\",\n" +
            "              \"value\": \"0\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"intro\": \"<p>Μια διαδικτυακή εφαρμογή με Java και MySQL.</p>\",\n" +
            "          \"introformat\": 1\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"fullname\": \"VHDL και Προηγμένη Λογική Σχεδίαση\",\n" +
            "      \"shortname\": \"vhdl\",\n" +
            "      \"timemodified\": 1467304203,\n" +
            "      \"assignments\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"fullname\": \"Ανοικτό Λογισμικό Android\",\n" +
            "      \"shortname\": \"android\",\n" +
            "      \"timemodified\": 1467304247,\n" +
            "      \"assignments\": []\n" +
            "    }\n" +
            "  ],\n" +
            "  \"warnings\": []\n" +
            "}";

    private static String ais0058MarkAsReadMessageJsonString = "{\n" +
            "  \"messageid\": 4,\n" +
            "  \"warnings\": []\n" +
            "}";

    private static String ais0058SecondCallForUnreadMessagesJsonString = "{\n" +
            "  \"messages\": [],\n" +
            "  \"warnings\": []\n" +
            "}";

    private static String ais0058ForumsByCourseJsonString = "{\n" +
            "    \"id\": 1,\n" +
            "    \"course\": 2,\n" +
            "    \"type\": \"news\",\n" +
            "    \"name\": \"Announcements\",\n" +
            "    \"intro\": \"General news and announcements\",\n" +
            "    \"introformat\": 1,\n" +
            "    \"assessed\": 0,\n" +
            "    \"assesstimestart\": 0,\n" +
            "    \"assesstimefinish\": 0,\n" +
            "    \"scale\": 0,\n" +
            "    \"maxbytes\": 0,\n" +
            "    \"maxattachments\": 1,\n" +
            "    \"forcesubscribe\": 1,\n" +
            "    \"trackingtype\": 1,\n" +
            "    \"rsstype\": 0,\n" +
            "    \"rssarticles\": 0,\n" +
            "    \"timemodified\": 1467304056,\n" +
            "    \"warnafter\": 0,\n" +
            "    \"blockafter\": 0,\n" +
            "    \"blockperiod\": 0,\n" +
            "    \"completiondiscussions\": 0,\n" +
            "    \"completionreplies\": 0,\n" +
            "    \"completionposts\": 0,\n" +
            "    \"cmid\": 1,\n" +
            "    \"numdiscussions\": 1,\n" +
            "    \"cancreatediscussions\": true\n" +
            "  }";

    private static String ais0058ForumDiscussionsJsonString = "{\n" +
            "  \"discussions\": [\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"name\": \"Παράδοση εκτυπωμένων σημειώσεων\",\n" +
            "      \"groupid\": -1,\n" +
            "      \"timemodified\": 1468482978,\n" +
            "      \"usermodified\": 2,\n" +
            "      \"timestart\": 0,\n" +
            "      \"timeend\": 0,\n" +
            "      \"discussion\": 3,\n" +
            "      \"parent\": 0,\n" +
            "      \"userid\": 2,\n" +
            "      \"created\": 1468482978,\n" +
            "      \"modified\": 1468482978,\n" +
            "      \"mailed\": 0,\n" +
            "      \"subject\": \"Παράδοση εκτυπωμένων σημειώσεων\",\n" +
            "      \"message\": \"<p>Το επόμενο Σάββατο θα μπορείτε να προμηθευτείτε σημειώσεις για το 3ο κεφάλαιο από το γραφείο μου.</p>\",\n" +
            "      \"messageformat\": 1,\n" +
            "      \"messagetrust\": 0,\n" +
            "      \"attachment\": \"\",\n" +
            "      \"totalscore\": 0,\n" +
            "      \"mailnow\": 0,\n" +
            "      \"userfullname\": \"Chris Koutroulis\",\n" +
            "      \"usermodifiedfullname\": \"Chris Koutroulis\",\n" +
            "      \"userpictureurl\": \"http://ais-temp.daidalos.teipir.gr/moodle/theme/image.php/clean/core/1467270948/u/f1\",\n" +
            "      \"usermodifiedpictureurl\": \"http://ais-temp.daidalos.teipir.gr/moodle/theme/image.php/clean/core/1467270948/u/f1\",\n" +
            "      \"numreplies\": \"0\",\n" +
            "      \"numunread\": 0,\n" +
            "      \"pinned\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"name\": \"Συνάντηση στο εργαστήριο\",\n" +
            "      \"groupid\": -1,\n" +
            "      \"timemodified\": 1467558208,\n" +
            "      \"usermodified\": 2,\n" +
            "      \"timestart\": 0,\n" +
            "      \"timeend\": 0,\n" +
            "      \"discussion\": 1,\n" +
            "      \"parent\": 0,\n" +
            "      \"userid\": 2,\n" +
            "      \"created\": 1467558208,\n" +
            "      \"modified\": 1467558208,\n" +
            "      \"mailed\": 0,\n" +
            "      \"subject\": \"Συνάντηση στο εργαστήριο\",\n" +
            "      \"message\": \"<p>Το ερόμενο Σάββατο, θα γίνει συνάντηση για να συζητηθούν απορίες σχετικά με το PROFIBus.</p>\",\n" +
            "      \"messageformat\": 1,\n" +
            "      \"messagetrust\": 0,\n" +
            "      \"attachment\": \"\",\n" +
            "      \"totalscore\": 0,\n" +
            "      \"mailnow\": 0,\n" +
            "      \"userfullname\": \"Chris Koutroulis\",\n" +
            "      \"usermodifiedfullname\": \"Chris Koutroulis\",\n" +
            "      \"userpictureurl\": \"http://ais-temp.daidalos.teipir.gr/moodle/theme/image.php/clean/core/1467270948/u/f1\",\n" +
            "      \"usermodifiedpictureurl\": \"http://ais-temp.daidalos.teipir.gr/moodle/theme/image.php/clean/core/1467270948/u/f1\",\n" +
            "      \"numreplies\": \"0\",\n" +
            "      \"numunread\": 0,\n" +
            "      \"pinned\": false\n" +
            "    }\n" +
            "  ],\n" +
            "  \"warnings\": []\n" +
            "}";

    public static String getAis0058GrantTokenJsonString() {
        return ais0058GrantTokenJsonString;
    }

    public static String getAis0058DeniedTokenJsonString() {
        return ais0058DeniedTokenJsonString;
    }

    public static String getFourCoursesAndTwoAssignmentsJsonString() {
        return fourCoursesAndTwoAssignmentsJsonString;
    }

    public static String getAis0058UserDetailsJsonString() {
        return ais0058UserDetailsJsonString;
    }

    public static String getAis0058UnreadMessagesJsonString() {
        return ais0058UnreadMessagesJsonString;
    }

    public static String getAis0058ReadMessagesJsonString() {
        return ais0058ReadMessagesJsonString;
    }

    public static String getAis0058MarkAsReadMessageJsonString() {
        return ais0058MarkAsReadMessageJsonString;
    }

    public static String getAis0058SecondCallForUnreadMessagesJsonString() {
        return ais0058SecondCallForUnreadMessagesJsonString;
    }

    public static String getAis0058ForumsByCourseJsonString() {
        return ais0058ForumsByCourseJsonString;
    }

    public static String getAis0058ForumDiscussionsJsonString() {
        return ais0058ForumDiscussionsJsonString;
    }
}
