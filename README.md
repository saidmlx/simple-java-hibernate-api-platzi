# Java Hibernate API

## Database configuration



docker run -d -p 33060:3306 --name mysql-db -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=platziprofesores platziprofesores

docker exec -it mysql-db mysql -p




## API

### Courses

{[/v1/courses],methods=[GET],produces=[application/json]}
{[/v1/course/{id}],methods=[GET],produces=[application/json]}
{[/v1/course],methods=[POST],produces=[application/json]}
{[/v1/course/teacher],methods=[PATCH],produces=[application/json]}
{[/v1/course/{id}],methods=[PATCH]}
{[/v1/course/{id}],methods=[DELETE]}

{[/v1/socialMedia],methods=[POST],produces=[application/json]}
{[/v1/socialMedias],methods=[GET],produces=[application/json]}
{[/v1/socialMedia/{id}],methods=[GET],produces=[application/json]}
{[/v1/socialMedia/{id}],methods=[PATCH],produces=[application/json]}
{[/v1/socialMedia/{id}],methods=[DELETE],produces=[application/json]}

{[/v1/teachers],methods=[GET],produces=[application/json]}
{[/v1/teacher/{id}],methods=[GET],produces=[application/json]}
{[/v1/teacher/{id}],methods=[PATCH],produces=[application/json]}
{[/v1/teacher/{id}],methods=[DELETE],produces=[application/json]}
{[/v1/teacher/image],methods=[POST],consumes=[multipart/form-data]}
{[/v1/teacher/{id}/image],methods=[GET],produces=[application/json]}
{[/v1/teacher/{id}/image],methods=[DELETE],produces=[application/json]}
{[/v1/teacher/socialMedias],methods=[PATCH]}
{[/v1/teacher],methods=[POST],produces=[application/json]} 

