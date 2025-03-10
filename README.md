# quarkus-keycloak

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-keycloak-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- JDBC Driver - H2 ([guide](https://quarkus.io/guides/datasource)): Connect to the H2 database via JDBC
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- OpenID Connect ([guide](https://quarkus.io/guides/security-openid-connect)): Verify Bearer access tokens and authenticate users with Authorization Code Flow
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)


### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

---


## Running Keycloak

To start a Keycloak instance locally, use the following command:

```shell
docker run --name keycloak -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -p 8180:8080 quay.io/keycloak/keycloak:17.0.0 start-dev
```


### What Does `start-dev` Do?
The `start-dev` flag runs Keycloak in **development mode**, making it easier to configure and debug by:
- ✅ **Using an embedded H2 database** instead of requiring an external database.
- ✅ **Allowing HTTP connections** (disables HTTPS enforcement).
- ✅ **Automatically detecting changes** in providers and configuration.
- ✅ **Skipping production optimizations**, making it faster for local testing.

### What Happens If You Don't Use `start-dev`?
- ❌ **Keycloak won’t start** unless a database is configured.
- ❌ **You’ll need an external database** (PostgreSQL, MySQL, etc.).
- ❌ **HTTPS might be required**, needing proper SSL certificates.
- ❌ **Startup is optimized for production**, making debugging harder.

### When Should You Use It?
- ✅ **For local development and testing** → Makes setup quick and easy.
- ❌ **Not for production** → Use `start` with a real database instead.


### Importing the Quarkus Realm in Keycloak
To configure Keycloak for Quarkus authentication, follow these steps:

1. **Download the Quarkus Realm Configuration File**  
   Download `quarkus-realm.json` from this URL:  
   <https://github.com/quarkusio/quarkus-quickstarts/blob/main/security-keycloak-authorization-quickstart/config/quarkus-realm.json>

2. **Import the Realm into Keycloak**
    - Log in to the Keycloak Admin Console at <http://localhost:8180/admin>.
    - Navigate to **"Realm Settings"** → **"Import"**.
    - Upload the `quarkus-realm.json` file.
    - Click **"Create"** to apply the settings.

This will pre-configure a realm with roles, clients, and other necessary settings for your Quarkus application.

---


## Authentication & Authorization Flow with Keycloak

### How JWT Authentication Works

1. **Keycloak generates the JWT** after authenticating the user.
2. **Client (Postman, frontend, etc.) sends the JWT** in the `Authorization` header when making API calls.
3. **Quarkus validates the JWT locally** (without calling Keycloak again):
    - ✅ **Decodes the JWT**
    - ✅ **Verifies the signature using Keycloak’s public key** (from `/protocol/openid-connect/certs`)
    - ✅ **Checks expiration (`exp`) & issuer (`iss`)**
    - ✅ **Checks if the user has the required roles** (`@RolesAllowed("manager")`)

❌ **No need to call Keycloak again to validate the token!**  
**Quarkus does everything locally**, making it super fast! 🚀🔥

### Why This Is Efficient?
- 🏎️ **No extra network calls to Keycloak** → Faster API response.
- 🔒 **More secure** → The JWT is signed and cannot be tampered with.
- ✅ **Stateless authentication** → No need to store sessions on the server.

This setup ensures secure and efficient authentication for your Quarkus service. 🚀  
