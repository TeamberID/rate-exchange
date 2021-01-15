Инструкция (Linux) по запуску приложения в Docker:

1)Убедитесь, что у вас установлен Docker:
$ docker -v
Если не установлен, воспользуйтесь мануалом: https://docs.docker.com/engine/install/ubuntu/

2)Скачайте данный репозиторий

3)Распакуйте скачанный архив и перейдите в директорию проекта (/rate-exchange)

4)Откройте терминал в данной директории и выполните следующие команды:
$ docker build -f Dockerfile -t docker-rate-exchange .
$ docker run -p 8080:8080 docker-rate-exchange
P.S. при необходимости используйте команду sudo

5)Вы можете проверить работу приложения в браузере:
http://localhost:8080/status/*CODE* (Вместо *CODE* - код валюты (USD, EUR итп))
