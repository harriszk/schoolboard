# Read Me

## User Stories
- Teachers can look up which student is in a particular class
- Student can find which courses they are taking are from a particular teacher.
- Be able to loop up which courses a teacher is teaching
- Students should be able to register/unregister for a course
- Be able to add new students, courses, and teachers
- Be able to delete existing students, courses, and teachers
- Be able to update existing new students, courses, and teachers

## Database Tables
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

![ER Diagram](https://github.com/harriszk/schoolboard/blob/main/src/main/resources/er_diagram.png?raw=true "ER Diagram")

[JavaDocs link](https://github.com/harriszk/schoolboard/blob/main/JavaDocs/index.html)

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