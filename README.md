# Messenger

This application was generated using JHipster 5.8.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v5.8.1](https://www.jhipster.tech/documentation-archive/v5.8.1).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1.  [Node.js][]: We use Node to run a development web server and build the project.
    Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

Download your proper package from https://nodejs.org/en/download/

## Running the server

To start the Messenger application run:

    ./gradlew -Pdev or gradlew.bat -Pdev

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mysql database in a docker container, run:

    docker-compose -f src/main/docker/mysql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mysql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./gradlew bootWar -Pprod jibDockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 5.8.1 archive]: https://www.jhipster.tech/documentation-archive/v5.8.1
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v5.8.1/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v5.8.1/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v5.8.1/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v5.8.1/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v5.8.1/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v5.8.1/setting-up-ci/
[node.js]: https://nodejs.org/
[yarn]: https://yarnpkg.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: http://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: http://jasmine.github.io/2.0/introduction.html
[protractor]: https://angular.github.io/protractor/
[leaflet]: http://leafletjs.com/
[definitelytyped]: http://definitelytyped.org/
