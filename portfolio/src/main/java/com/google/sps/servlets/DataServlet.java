// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

ArrayList<String> commentList = new ArrayList<>();

private void setupCommentsOnce(){
    if (commentList.size() == 0){
    commentList.add("Hi!");
    commentList.add("This is the beginnings of a comment feature!");
    commentList.add("More to come soon!");
    }

}



  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

      //setup the hardcoded messages
    //setupCommentsOnce();

    //convert comment list to json
    String json = convertToJsonUsingGson(commentList);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  /**
   * Converts a ServerStats instance into a JSON string using the Gson library. Note: We first added
   * the Gson library dependency to pom.xml.
   */
  private String convertToJsonUsingGson(ArrayList<String> commentList) {
    Gson gson = new Gson();
    String json = gson.toJson(commentList);
    return json;
  }

    @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String text = getParameter(request, "text-input", "");
    
    commentList.add(text);

    //convert comment list to json
    String json = convertToJsonUsingGson(commentList);

    response.setContentType("application/json;");
    response.getWriter().println(json);



    // Respond with the result.
    //response.setContentType("text/html;");
    //response.getWriter().println(text);

    response.sendRedirect("/index.html");
  }

  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}

