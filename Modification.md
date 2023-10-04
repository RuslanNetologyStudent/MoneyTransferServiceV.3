    Исправлено:

    1. проект пересобран заново;
    2. url вынесен в application.propeties;
    3. используестя конструктор @RequiredArgsConstructor в классах TransferController, TransferServiceImpl, 
    CardValidator, ConfirmationValidator;
    4. создан отдельный класс ExceptionsHandler;
    5. в проекте использован тип Record;
    6. валидация вынесена в отдельный класс.