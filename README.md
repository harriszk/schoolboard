# Read Me

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
       <li><a href="#Current-version-functionality">Current version functionality</a></li>
        <li><a href="#built-with">Used technology</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
     <li><a href="#Implementation">Implementation</a>
<ul> <li><a href="#Database">Database</a></li>
     <li>Java code</li>
     <li><a href="#Available-Endpoints">Endpoints</a>
       <ul>
         <li><a href='#Courses-endpoints'>Courses endpoints</a></li>
         <li><a href='#Students-endpoints'>Students endpoints</a></li>
         <li><a href='#Courses-endpoints'>Courses endpoints</a></li>
         <li><a href='#Teacher-endpoints'>Teacher endpoints</a></li>
       </ul>
     </li>
     <li>Tests</li>
</ul>
</li>
  </ol>
</details>

## User Stories
- Teachers can look up which student is in a particular class
- Student can find which courses they are taking are from a particular teacher.
- Be able to loop up which courses a teacher is teaching
- Students should be able to register/unregister for a course
- Be able to add new students, courses, and teachers
- Be able to delete existing students, courses, and teachers
- Be able to update existing new students, courses, and teachers

## Database

## Schema
![ER Diagram](https://github.com/harriszk/schoolboard/blob/main/src/main/resources/er_diagram.png?raw=true "ER Diagram")

[JavaDocs link](JavaDocs/index.html)

## Available Endpoints


#### Courses endpoints
----------------
#### Get all of the courses

###### Request
```GET /courses```

###### Response
Status code will return a 200 with a similar body.
```json
[
  {
    "id": 1,
    "subject": "MATH",
    "number": 15000,
    "title": "Number Systems",
    "creditHours": 4.0,
    "teacherId": 1
  },
  {
    "id": 2,
    "subject": "BIOL",
    "number": 12300,
    "title": "Biology 101",
    "creditHours": 4.0,
    "teacherId": 1
  },
  ...
]
```
----------------
#### Add a new course

###### Request
```POST /course```

Body:
```json
{
  "id": 100,
  "subject": "MATH",
  "number": 32800,
  "title": "Metric and Topological Spaces",
  "creditHours": 4.0,
  "teacherId": 1
}
```

###### Response
If successful a status code of 200 will be returned with the following body.
```Successfully added course!```

Errors
- If the course id that was sent is already in use then a status code of 400 is returned with the following body.
```Exception.ItemAlreadyExistsException: Course already exists```
----------------
#### Update an existing course

###### Request
```PUT /course```

Body:
```json
{
  "id": 25,
  "subject": "PHYS",
  "number": 30400,
  "title": "Special Relativity",
  "creditHours": 4.0,
  "teacherId": 18
}
```

###### Response
Status code will return a 200 with the following body.
```Successfully updated course!```

----------------
#### Delete an existing course

###### Request
```DELETE /course/{course_id}```

###### Response

If successful a status code of 200 will be returned with the following body.
```Successfully deleted course!```

If no course with that id exists then a status code of 400 will be returned with the following body.
```Exception.ItemDoesNotExistException: Course does not exist```

----------------
#### Get a course by its id

###### Request
```GET /course/{course_id}```

###### Response

If successful a status code of 200 will be returned with a similar body for the specific course requested.
```json
{
  "id": 45,
  "subject": "CHEM",
  "number": 34200,
  "title": "Organic Chemistry II",
  "creditHours": 4.0,
  "teacherId": 25
}
```

If no such course with the requested id exists then the request is unsuccessful and a status code of 404 will be returned with the following body.
```No course with that id!```

----------------
#### Get all of the courses that a teacher is teaching

###### Request
```GET /course/teacher/{teacher_id}```

###### Response

The response will always return a 200 status code. If there exists a teacher whose id is associated with a course then a similar body as below will be returned.
```json
[
  {
    "id": 55,
    "subject": "CS",
    "number": 17500,
    "title": "C/C++ Programming",
    "creditHours": 4.0,
    "teacherId": 14
  },
  {
    "id": 64,
    "subject": "CS",
    "number": 45000,
    "title": "Principles of Software Engineering",
    "creditHours": 4.0,
    "teacherId": 14
  },
  ...
]
```
Else, an empty array will be returned in the body.
```json
[]
```

----------------
#### Get all of the courses that a student is taking

###### Request
```GET /course/student/{student_id}```

###### Response
The response will always return a 200 status code. If there exists a student with the provided id and that student is associated with some courses then a similar body as below will be returned. 
```json
[
  {
    "id": 2,
    "subject": "BIOL",
    "number": 12300,
    "title": "Biology 101",
    "creditHours": 4.0,
    "teacherId": 1
  },
  {
    "id": 5,
    "subject": "ENG",
    "number": 20400,
    "title": "Introduction to Fiction",
    "creditHours": 3.0,
    "teacherId": 3
  },
  ...
]
```
Else, an empty array will be returned in the body.
```json
[]
```
----------------



#### Get all of the courses that a student is taking with a specific teacher

###### Request
```GET /course/student/{student_id}/teacher/{teacher_id}```

###### Response
The response will always return a 200 status code. If there exists a student with the provided id as well as a teacher with the provided id and that student is taking a course/courses with that teacher, then a similar body as below will be returned.
```json
[
  {
    "id": 1,
    "subject": "MATH",
    "number": 15000,
    "title": "Number Systems",
    "creditHours": 4.0,
    "teacherId": 1
  },
  {
    "id": 2,
    "subject": "BIOL",
    "number": 12300,
    "title": "Biology 101",
    "creditHours": 4.0,
    "teacherId": 1
  },
  ...
]
```
Else, an empty array will be returned in the body.
```json
[]
```
----------------

#### Teacher endpoints

##### Get all of  a teachers

###### Request
```GET /teachers```

###### Response
The response will always return a 200 status code.
If there exists a teacher then a similar body as below will be returned otherwise will be return an empty array
```json
[
    {
        "id": 1,
        "name": "Zachary Harris"
    },
    {
        "id": 2,
        "name": "Ralph Fatkullin"
    }
...
  ]
```
----------------

##### Get a teacher

###### Request
```GET /teachers/{id}```

###### Response
The response will always return a 200 status code.
If there exists a teacher then a similar body as below will be returned.

```json
{
"id": 1,
"name": "Zachary Harris"
}
```
Else, a message will be returned in the body.

```json
No teacher with that id!
```
----------------

##### Get all courses of a particular teacher by a name

###### Request
```GET /teachers-courses/{name}```

###### Response
The response will always return a 200 status code.
If there exists a teacher and courses then a similar body as below will be returned.

```json
[
{
"id": 4,
"subject": "ENG",
"number": 20200,
"title": "Literary Interpretation",
"creditHours": 3.0,
"teacherId": 3
},
...
]
```
Else, a message will be returned in the body.

```json
Exception.ItemDoesNotExistException: teacher Walt does not exist
```
----------------

##### Add a new teacher

###### Request
```POST /teachers```
```json
{
"id": 29,
"name": "Edgar F. Codd"
}
```

###### Response
The response will always return a 200 status code.
If there exists a teacher and courses then a similar body as below will be returned.

```json
  Successfully added teacher!
```
Else, a message will be returned in the body.

```json
Exception.ItemAlreadyExistsException: teacher already exists
```
----------------

##### Update a teacher

###### Request
```PUT /teachers```
```json
{
  "id": 29,
  "name": "Edgar "
}
```

###### Response
The response will always return a 200 status code.
If there exists a teacher then a similar body as below will be returned.

```json
  Successfully updated teacher!
```
Else, a message will be returned in the body.

```json
Exception.ItemDoesNotExistException: teacher does not exist
```
----------------


##### Delete a teacher

###### Request
```DELETE /teachers/{id}```
```json
{
  "id": 29,
  "name": "Edgar "
}
```

###### Response
The response will always return a 200 status code.
If there exists a teacher then a similar body as below will be returned.

```json
 Successfully deleted teacher!
```
Else, a message will be returned in the body.

```json
Exception.ItemDoesNotExistException: teacher does not exist
```
----------------
