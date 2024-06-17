# Афиша мероприятий

В этом сервисе можно предложить какое-либо событие от выставки до похода в кино и собрать компанию для участия в нём. Сервис, который позволяет публиковать, редактировать, собирать подборки ивентов и записываться на мероприятия.
Также происходит сбор статистки: кто, куда, сколько и когда заходил. Это позволяет анализировать событие: интересно оно или нет, сколько уникальных пользователей его просматривали и прочее.
Проект реализован при помощи двух микросервисов: один позволяет собирать статистику, другой реализует сам сервис мероприятий.

Чтобы запустить проект нужно его клонировать, собрать и с помощью Docker Compose запусить docker-compose.yml, он находится в корневой директории проекта. Он содержит конфигурации для развёртывания всех необходимых сервисов.

Для корректного развёртывания и работы проекта необходимы следующие компоненты:\
+**Java**: версия 11 или выше\
+**Maven**: версия 3.6.3 или выше\
+**Docker**: версия 20.10 или выше\
+**Docker Compose**: версия 1.27 или выше

Стек технологий: Java, Spring Boot, Maven, PostgreSQL, Docker

