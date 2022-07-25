# TASK 1 - update

## Goals App - Spring Boot RESTful Web Service

Backend application for goals app, we used this service to create, update and manage your personal goals.

Service Hosted URL : [a828bf20ba9ba4ea89c21d97075c3066-1106765093.ap-south-1.elb.amazonaws.com
](a828bf20ba9ba4ea89c21d97075c3066-1106765093.ap-south-1.elb.amazonaws.com
)

*The above service is running on amazon kubernetes cluster.*

- Will be able to create a new Server Record in Mongo DB.
- Will be able to update/modify an existing Server Record.
- Will be able to see the list of servers you have created and search it by name.
- Will also be able to delete the server record when it is needed.

| # | Libraries Used | Version |
|--|--|--|
| 1 | Spring Boot | 2.6.6 |
| 2 | Spring Data - Mongo | 2.6.6

**Code Architecture:**  MVC

### Model
For this task a single model is used to complete the task objective.

```
String id;
String name;
String language;
String framework;
Date createdDateTime;
Date lastModifiedDateTime;
```
the detailed model implementation can be seen at `model/ServerModel` class.

### Controller
The controller is the part where we define the business logic of the application.

`/goal` is the  base-url of `ServerController`.

- createServer Controller
- getAllServers Controller with pagination and Search filter
- getServerById Controller
- updateServerById Controller
- deleteServerById Controller

### View

**1. Create Server**

```shell
curl --location --request POST '${BASE_URL}/server/createServer' \ 
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "ubuntu",
    "language": "java",
    "framework": "django"
}'
```

**Response**

```json
{
    "data": {
        "id": "625e8399e0367e6d185c4c2b",
        "name": "ubuntu",
        "language": "java",
        "framework": "django",
        "createdDateTime": "2022-04-19T09:40:41.416+00:00",
        "lastModifiedDateTime": "2022-04-19T09:40:41.416+00:00"
    },
    "message": "Success"
}
```

**2. Get All Servers**

```shell
curl --location --request GET '${BASE_URL}/server/getAllServers'
```

**Response**

```json
{
    "totalItems": 2,
    "data": [
        {
            "id": "625e8642a679350ff73938d3",
            "name": "windows",
            "language": "python",
            "framework": "flask",
            "createdDateTime": "2022-04-19T09:52:02.528+00:00",
            "lastModifiedDateTime": "2022-04-19T09:52:02.528+00:00"
        },
        {
            "id": "625e8399e0367e6d185c4c2b",
            "name": "ubuntu",
            "language": "java",
            "framework": "django",
            "createdDateTime": "2022-04-19T09:40:41.416+00:00",
            "lastModifiedDateTime": "2022-04-19T09:40:41.416+00:00"
        }
    ],
    "totalPages": 1,
    "message": "Success",
    "currentPage": 0
}
```

*with pagination*

```shell
curl --location --request GET '${BASE_URL}/server/getAllServers?page=1&size=1'
```

***Response***

```json
{
    "totalItems": 2,
    "data": [
        {
            "id": "625e8399e0367e6d185c4c2b",
            "name": "ubuntu",
            "language": "java",
            "framework": "django",
            "createdDateTime": "2022-04-19T09:40:41.416+00:00",
            "lastModifiedDateTime": "2022-04-19T09:40:41.416+00:00"
        }
    ],
    "totalPages": 2,
    "message": "Success",
    "currentPage": 1
}
```

*with Search filter*

Parameters: **searchQuery, page, size**

```shell
curl --location --request GET '${BASE_}/server/getAllServers?searchQuery=win'
```

**Response**

```json
{
    "totalItems": 1,
    "data": [
        {
            "id": "625e8642a679350ff73938d3",
            "name": "windows",
            "language": "python",
            "framework": "flask",
            "createdDateTime": "2022-04-19T09:52:02.528+00:00",
            "lastModifiedDateTime": "2022-04-19T09:52:02.528+00:00"
        }
    ],
    "totalPages": 1,
    "message": "Success",
    "currentPage": 0
}
```

**3. Get Server By ID**

```shell
curl --location --request GET '${BASE_URL}/server/getServerById/625e8642a679350ff73938d3'
```

**Response**

```json
{
  "data": {
    "id": "625e8642a679350ff73938d3",
    "name": "windows",
    "language": "python",
    "framework": "flask",
    "createdDateTime": "2022-04-19T09:52:02.528+00:00",
    "lastModifiedDateTime": "2022-04-19T09:52:02.528+00:00"
  },
  "message": "Success"
}
```

**4. Update Server By ID**

```shell
curl --location --request PUT '${BASE_URL}/server/updateServer/625e8642a679350ff73938d3' \
--header 'Content-Type: application/json' \
--data-raw '{ "name": "macos" }'
```

**Response**

```json
{
  "data": {
    "id": "625e8642a679350ff73938d3",
    "name": "macos",
    "language": "python",
    "framework": "flask",
    "createdDateTime": "2022-04-19T09:52:02.528+00:00",
    "lastModifiedDateTime": "2022-04-19T09:58:00.134+00:00"
  },
  "message": "Success"
}
```

**5. Delete Server By ID**

```shell
curl --location --request DELETE '${BASE_URL}/server/deleteServer/625e5f18a60ce079f7a230af'
```

**Response**

```json
{
  "message": "Success"
}
```
