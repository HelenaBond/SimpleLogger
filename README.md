Реализация Системы Логгирования
1. Логгирование сообщений разных уровней (DEBUG, INFO, WARN, ERROR).
2. Возможность конфигурирования логгеров (например, уровень логирования, формат сообщений, выходные потоки (консоль, файл и т.д.)).
3. Возможность добавления различных аппендеров (console, file).
4. Поддержка конфигурации через файл конфигурации.
5. Асинхронное логгирование (опционально).

### Как настраивать логгер
Если настройки лежат в файле с расширением .properties.

log.appenders=test - тут должны быть перечислены через запятую все имена аппендеров которые будут настроены ниже.

log.appender.test.type=file - затем к приставке log.appender. добавляете одно из имён
перечисленных выше и суффикс .type . В значении указываете тип аппендера для которого будут производится настройки.
Например file или console или кастомный аппендер.

Ниже установлены значения по умолчанию.

Для всех аппендеров - наследников AbstractAppender.

log.appender.test.dateTimeFormat=yyyy-MM-dd HH:mm:ss

log.appender.test.messageFormat=[%s] [%s] %s

log.appender.test.from=INFO

log.appender.test.to=ERROR

Для FileAppender

log.appender.test.filePath="log.txt

### Как создать логгер
Создайте один логгер на всё приложение.

AppendersRegister appenders = new AppendersRegister();

LoggerConfigurator configurator = new LoggerJsonConfigurator("Путь к файлу с настройками", appenders);

или

LoggerConfigurator configurator = new LoggerPropertiesConfigurator("Путь к файлу с настройками", appenders);

и затем

Logger logger = configurator.createLogger();

### Как добавить собственную реализацию Appender в дополнение к существующим
До вызова метода создания логгера добавьте ваш аппендер используя метод:
appenders.registerAppender("type", CustomAppender.class);

Вместо "type" вставьте имя которое вы будете использовать для настройки файла конфигурации
в качестве значения для ключа type. Вместо CustomAppender.class вставьте название вашего кастомного аппендера.
Самый простой способ создания кастомного аппендера - это наследование от класса AbstractAppender.
Теперь вы можете настраивать свой аппендер через файл конфигурации по примеру выше.

### Как добавить собственную реализацию PropertiesDeserializer в дополнение к существующим
используйте аннотацию PropsDeserializer(using = CustomPropsDeserializer.class) над полем которое нужно десериализовать.
Где CustomPropsDeserializer.class ваша кастомная реализация интерфейса PropertiesDeserializer<T>

### Как добавить собственную реализацию поддержки нового формата.
Можно отнаследоваться от AbstractConfigurator. 
При создании объекта конфигуратора для работы с существующими аппендерами
в конструктор нужно поместить регистратор аппендеров.
AppendersRegister appenders = new AppendersRegister();
