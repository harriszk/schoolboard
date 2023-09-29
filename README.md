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
     <li><a href="#Available-Endpoints">Endpoints</a></li>
     <li>Tests</li>
</ul>
</li>
  </ol>
</details>

## Database
### Tables
- Students
  - id
  - name
  - email
  - phone_number
- Teachers
  - id
  - name
- Courses
  - id
  - name
  - teacher_id
- Student Courses Junction
  - student_id
  - course_id

##Schema
![ER Diagram](https://github.com/harriszk/schoolboard/blob/main/src/main/resources/er_diagram.png?raw=true "ER Diagram")

[JavaDocs link](https://github.com/harriszk/schoolboard/blob/main/JavaDocs/index.html)

## User Stories
- Teachers can look up which student is in a particular class
- Student can find which courses they are taking are from a particular teacher.
- Be able to loop up which courses a teacher is teaching
- Students should be able to register/unregister for a course
- Be able to add new students, courses, and teachers
- Be able to delete existing students, courses, and teachers
- Be able to update existing new students, courses, and teachers

Add a new flight
POST localhost:8080/flight, with a request body
{"flight_id":1, "departure_city":"tampa", "arrival_city":"nyc"}
Response
Should be 201 Created with the response body
{"flight_id":1, "departure_city":"tampa", "arrival_city":"nyc"}
if successful,
400 client error with no response body if unsuccessful (id already in use, city doesn't exist, etc)

## Available Endpoints

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

----------------
#### Get a course by its id

###### Request
```GET /course/{course_id}```

###### Response

----------------
#### Get all of the courses that a teacher is teaching

###### Request
```GET /course/teacher/{teacher_id}```

###### Response

----------------
#### Get all of the courses that a student is taking

###### Request
```GET /course/student/{student_id}```

###### Response

----------------
#### Get all of the courses that a student is taking with a specific teacher

###### Request
```GET /course/student/{student_id}/teacher/{teacher_id}```

###### Response

----------------