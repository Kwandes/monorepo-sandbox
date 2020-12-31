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
