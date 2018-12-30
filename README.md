# Java Hibernate API

## Set Up Database Database configuration


Run image

```bash
docker run -d -p 33060:3306 --name mysql-db -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=courses saidmlx/mysql-5-courses
``

Get into MySQL terminal with pasword __secret__

```bash
docker exec -it mysql-db mysql -p
```

if Docker scared you, use the script __*database.sql*__ in you local MySQL 



## API

### Courses


{[/v1/courses],methods=[GET]}
{[/v1/course/{id}],methods=[GET]}
{[/v1/course],methods=[POST]}
{[/v1/course/teacher],methods=[PATCH]}
{[/v1/course/{id}],methods=[PATCH]}
{[/v1/course/{id}],methods=[DELETE]}

{[/v1/socialMedia],methods=[POST]}
{[/v1/socialMedias],methods=[GET]}
{[/v1/socialMedia/{id}],methods=[GET]}
{[/v1/socialMedia/{id}],methods=[PATCH]}
{[/v1/socialMedia/{id}],methods=[DELETE]}

{[/v1/teachers],methods=[GET]}
{[/v1/teacher/{id}],methods=[GET]}
{[/v1/teacher/{id}],methods=[PATCH]}
{[/v1/teacher/{id}],methods=[DELETE]}
{[/v1/teacher/image],methods=[POST]}
{[/v1/teacher/{id}/image],methods=[GET]}
{[/v1/teacher/{id}/image],methods=[DELETE]}
{[/v1/teacher/socialMedias],methods=[PATCH]}
{[/v1/teacher],methods=[POST]} 

