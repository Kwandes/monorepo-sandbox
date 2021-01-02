# Project Setup
This file describes the setup process of the repository, with the commands and steps needed to succesffuly recreate it

## Workspace setup
Create a new workspace in the root folder of the repository
`npx create-nx-workspace@latest app-sandbox`

### Java Project setup
*[guide](https://www.linkedin.com/pulse/integrating-spring-boot-application-inside-nx-workspace-tine-kondo/)* \
Navigate into the NX workspace directory `cd app-sandbox` \
Install a dependancy for Java and Spring 
`npm install @nxrocks/nx-spring-boot --save-dev`

Generate a Java app
`nx g @nxrocks/nx-spring-boot:app java-api` \
When prompted to, choose **Maven**, **Jar**, **Java 11** and **Java**. \
GroupId is your domain, in our case it's **dev.hotdeals** \
ArtifactId is the name of the app, **api** \
For the package name, we combine the domain and the app name **dev.hotdeals.api** \
Description is quite self explanatory \
For the dependancies we don't pass anything, and we will simply add them by hand. Why? Because I'm lazy and this is less error-prone

The NX schematic for jav is imporperly set some of the properties, which have to be adjusted *(groupId, name and corresponding main class)* \
Check details of this commit (where this line was added) to see what has to be adjusted.

After this, I've copied over the entire API from [this repository](https://github.com/Kwandes/sonito). You can visit it for details on how the api was created. \

The java-api app requires one environemnt variable, **APP_SANDBOX_JDBC_URL**, which follows a format of `jdbc:mysql://username:password@url:port/app_sandbox?serverTimezone=UTC&characterEncoding=UTF-8`

### React Project Setup
Navigate into the NX workspace directory `cd app-sandbox` \
Install a schematic for React `npm install --save-dev @nrwl/react`

Generate a React app `nx generate @nrwl/react:app react-student-app` \
When prompted to, choose the following: \
Stylesheet format: **SASS** \
Router: **Yes** *(We will need it in the future anyway, might as well add it)*

Update the package.json of the workspace with new scripts. Allows things like serving the application to be done via npm \
Update the test script to run tests for all projects instead of just the default one

Copy the react app code from [this repository](https://github.com/Kwandes/sonito). \
Requires one new dependancy, `react-toastify` `npm install --save-dev react-toastify`

*The app actually uses css. Future apps would be generated with css instead of scss*