
package context;

import java.util.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

    public class ScenarioContext {

        private static ScenarioContext instance;

        private RequestSpecification request;
        private Response response;

        private String token;
        private Map<String, String> rowData;

        private List<Integer> programIds = new ArrayList<>();
        private List<Integer> batchIds = new ArrayList<>();
        private List<String> programNames = new ArrayList<>();
        private List<String> batchNames = new ArrayList<>();
        private List<String> skillNames = new ArrayList<>();
        private List<Integer> skillIds = new ArrayList<>();


        private String userId;
        private String role_id;
        private String programId;
        private String currentSkillId;
        private String currentSkillName;



        private ScenarioContext() {}

        public static synchronized ScenarioContext getInstance() {
            if (instance == null) {
                instance = new ScenarioContext();
            }
            return instance;
        }

//  REQUEST / RESPONSE

        public void setRequest(RequestSpecification request) {
            this.request = request;
        }

        public RequestSpecification getRequest() {
            return request;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        public Response getResponse() {
            return response;
        }

// AUTH

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

// TEST DATA

        public void setRowData(Map<String, String> rowData) {
            this.rowData = rowData;
        }

        public Map<String, String> getRowData() {
            return rowData;
        }

//PROGRAM

        public void addProgramId(int id) {
            programIds.add(id);
        }

        public int getProgramId(int index) {
            return programIds.get(index);
        }

        public void addProgramName(String name) {
            programNames.add(name);
        }

        public String getProgramName(int index) {
            return programNames.get(index);
        }

// BATCH

        public void addBatchId(int id) {
            batchIds.add(id);
        }

        public int getBatchId(int index) {
            return batchIds.get(index);
        }
        public void addBatchName(String name) {
            batchNames.add(name);
        }
        public String getBatchName(int index) {
            return batchNames.get(index);
        }


        // SKILL ID
        public void addSkillId(int id) {
            skillIds.add(id);
        }

        public int getSkillId(int index) {
            return skillIds.get(index);
        }

        public void setCurrentSkillId(String skillId) {
            this.currentSkillId = skillId;
        }

        public String getCurrentSkillId() {
            return currentSkillId;
        }
        public void addSkillName(String name) {
            skillNames.add(name);
        }

        public String getSkillName(int index) {
            return skillNames.get(index);
        }

        public void setCurrentSkillName(String name) {
            this.currentSkillName = name;
        }

        public String getCurrentSkillName() {
            return currentSkillName;
        }


        public  void setUserId(String userId) {
            this.userId = userId;

        }
        public  void setRoleId(String role_id) {
            this.role_id = role_id;
        }

        public void setProgramId(String program_Id) {
            this.programId = program_Id;
        }
        public void setSkillId(String skill_Id) {
            this.currentSkillId= skill_Id;
        }

        public String getUserId() {
            return userId;
        }
        public String getRoleId() {
            return role_id;
        }
        public String getSkillId() {
            return currentSkillId;
        }
            public String getSkillName() {
            return currentSkillName;
            }





// CLEANUP

        public void clear() {
            request = null;
            response = null;
            token = null;
            rowData = null;

            programIds.clear();
            programNames.clear();
            batchIds.clear();
            skillIds.clear();
            skillNames.clear();
            currentSkillId = null;
            currentSkillName = null;
        }
    }

