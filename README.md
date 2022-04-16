# TASK 1

## Goals App - Spring Boot RESTful Web Service

Backend application for goals app, we used this service to create, update and manage your personal goals.

Service Hosted URL : [ab4bb3203e58540ecbf84530e43db439-2033371937.ap-south-1.elb.amazonaws.com](ab4bb3203e58540ecbf84530e43db439-2033371937.ap-south-1.elb.amazonaws.com)

*The above service is running on amazon kubernetes cluster.*

- Will be able to create a new goal.
- Will be able to update/modify an existing goal.
- Will be able to see the list of goals you have with their due data.
- Will also be able to delete the goal after its completed.

| # | Libraries Used | Version |
|--|--|--|
| 1 | Spring Boot | 2.6.6 |
| 2 | Spring Data - Mongo | 2.6.6

**Code Architecture:**  MVC

### Model
For this task a single model is used to complete the task objective.

```
String id;  
String goalName;  
String description;  
Date dueDateTime;
Date createdDateTime;
Date lastModifiedDateTime;
```
the detailed model implementation can be seen at `model/GoalModel` class.

### Controller
The controller is the part where we define the business logic of the application.

`/goal` is the  base-url of `GoalController`.

- createGoal Controller
- getAllGoals Controller with pagination
- getGoalById Controller
- updateGoalById Controller
- deleteGoalById Controller

### View

**1. Create Goal**

```
curl --location --request POST '${BASE_URL}/goal/' --header 'Content-Type: application/json' --data-raw '{
"goalName": "Exercise Daily",
"description": "1 Hour of daily Exercise"
}'
```

**Response**

```
{
	"data": {
		"id": "625aad71d426925ff438e128",
		"goalName": "Exercise Daily",
		"description": "1 Hour of daily Exercise",
		"dueDateTime": null,
		"createdDateTime": "2022-04-16T11:50:09.924+00:00",
		"lastModifiedDateTime": "2022-04-16T11:50:09.924+00:00"
	},
	"message": "Success"
}
```

**2. Get All Goals**

```
curl --location --request GET '${BASE_URL}/goal/'
```

**Response**

```
{
	"totalItems": 2,
	"data": [
	{
		"id": "625aad71d426925ff438e128",
		"goalName": "Exercise Daily",
		"description": "1 Hour of daily Exercise",
		"dueDateTime": null,
		"createdDateTime": "2022-04-16T11:50:09.924+00:00",
		"lastModifiedDateTime": "2022-04-16T11:50:09.924+00:00"
	},
	{
		"id": "625aa4ef4aae93081501311f",
		"goalName": "Complete your story",
		"description": "Write 10 pages daily",]
		"dueDateTime": "2022-04-23T06:31:14.000+00:00",
		"createdDateTime": "2022-04-16T11:13:51.312+00:00",
		"lastModifiedDateTime": "2022-04-16T11:13:51.312+00:00"
	}
	],
	"totalPages": 1,
	"message": "Success",
	"currentPage": 0
}
```

*with pagination*

```
curl --location --request GET '${BASE_URL}/goal/?page=1&size=1'
```

***Response***

```
{
	"totalItems": 2,
	"data": [
		{
			"id": "625aa4ef4aae93081501311f",
			"goalName": "Complete your story",
			"description": "Write 10 pages daily",
			"dueDateTime": "2022-04-23T06:31:14.000+00:00",
			"createdDateTime": "2022-04-16T11:13:51.312+00:00",
			"lastModifiedDateTime": "2022-04-16T11:13:51.312+00:00"
		}
	],
	"totalPages": 2,
	"message": "Success",
	"currentPage": 1
}
```

**3. Get Goal By ID**

```
curl --location --request GET '${BASE_URL}/goal/625aa4ef4aae93081501311f'
```

**Response**

```
{
	"data": {
		"id": "625aa4ef4aae93081501311f",
		"goalName": "Complete your story",
		"description": "Write 10 pages daily",
		"dueDateTime": "2022-04-23T06:31:14.000+00:00",
		"createdDateTime": "2022-04-16T11:13:51.312+00:00",
		"lastModifiedDateTime": "2022-04-16T11:13:51.312+00:00"
	},
	"message": "Success"
}
```

**4. Update Goal**

```
curl --location --request PUT '${BASE_URL}/goal/625aa4ef4aae93081501311f' --header 'Content-Type: application/json' --data-raw '{
	"id": "625aa4ef4aae93081501311f",
	"goalName": "Complete your story",
	"description": "Write 15 pages daily",
	"dueDateTime": "2022-04-23T06:31:14.000+00:00"
}'
```

**Response**

```
{
	"data": {
		"id": "625aa4ef4aae93081501311f",
		"goalName": "Complete your story",
		"description": "Write 15 pages daily",
		"dueDateTime": "2022-04-23T06:31:14.000+00:00",
		"createdDateTime": "2022-04-16T11:13:51.312+00:00",
		"lastModifiedDateTime": "2022-04-16T11:59:52.492+00:00"
	},
	"message": "Success"
}
```

**5. Delete Goal**

```
curl --location --request DELETE '${BASE_URL}/goal/625aa4ef4aae93081501311f'
```

**Response**

```
{
	"message": "Success"
}
```