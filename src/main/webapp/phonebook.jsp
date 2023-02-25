<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/phonebook.css"/>
    <script src="js/bootstrap.bundle.js" defer></script>
    <script src="js/script.js" defer></script>
    <title>Phone book JSP</title>
</head>
<body>
<div class="content">
    <table class="table table-bordered contact-table">

        <form class="filter-container " method="get" action="phonebook">
            <label class="filter-label mb-0 mr-2">Введите текст:
                <%String filter = request.getParameter("filter");%>
                <input type="text" name="filter" class="form-control input-sm"
                       value='<%=filter == null ? "" : filter%>'/>
            </label>
            <button type="submit" class="btn btn-primary">Отфильтровать</button>
        </form>
        <form class="p-5 m-4 filter-container" method="get" action="phonebook">
            <input type="hidden" name="filter" value=""/>
            <button type="submit" class="ml-1 btn btn-primary">Сбросить фильтр</button>
        </form>


        <table class="table table-bordered contact-table" id="contacts_table">
            <thead>
            <tr>
                <th>
                    <label class="select-all-label">
                        <input type="checkbox" id="main_checkbox" title="Выбрать"/>
                    </label>
                </th>
                <th>№</th>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Телефон</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody>

            <jsp:useBean id="contactList" scope="request" type="java.util.List"/>
            <c:forEach var="contact" items="${contactList}">
                <tr>
                    <td>
                        <label class="select-me-label">
                            <input type="checkbox" class="select-me" name="contactId" value="${contact.getId()}"/>
                        </label>
                    </td>
                    <td><c:out value="${contact.getId()}"/></td>
                    <td><c:out value="${contact.getLastName()}"/></td>
                    <td><c:out value="${contact.getFirstName()}"/></td>
                    <td><c:out value="${contact.getPhone()}"/></td>
                    <td>
                        <form action="remove" method="post">
                            <input type="hidden" name="contactId" value="${contact.getId()}"/>
                            <button type="submit" class='btn btn-primary'>Удалить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>


        <button type="button" id="remove_checked_contacts_button" class="btn btn-primary">Удалить выбранные</button>
        <label class="server-error-message-container">
            <span class="error-message">
             <jsp:useBean id="removingResponse" scope="request"
                          type="ru.academit.podlatov.phonebookjsp.service.RemovingActionResponse"/>
             <c:if test="${removingResponse.error}">
                 <c:out value="${removingResponse.message}"/>
             </c:if>
        </span>
        </label>
        <br>

        <label class="server-error-message-container">
        <span class="error-message">
             <jsp:useBean id="contactValidation" scope="request"
                          type="ru.academit.podlatov.phonebookjsp.service.ContactValidation"/>
             <c:if test="${not empty contactValidation.globalError}">
                 <c:out value="${contactValidation.globalError}"/>
             </c:if>
        </span>
        </label>

        <form action="add" method="POST">
            <div>
                <label class="form-label">
                    <span class="form-field">Фамилия:</span>
                    <jsp:useBean id="currentContact" scope="request"
                                 type="ru.academit.podlatov.phonebookjsp.model.Contact"/>
                    <input type="text" class="ml-1 form-control input-sm form-input" name="lastName"
                           value='<%=currentContact.getLastName() == null ? "" : currentContact.getLastName() %>'/>
                    <span class="error-message">
                     <c:if test="${not empty contactValidation.lastNameError}">
                         <c:out value="${contactValidation.lastNameError}"/>
                     </c:if>
                </span>
                </label>
            </div>
            <div>
                <label class="form-label">
                    <span class="form-field">Имя:</span>
                    <input type="text" class="ml-1 form-control input-sm form-input" name="firstName"
                           value='<%=currentContact.getFirstName() == null ? "" : currentContact.getFirstName() %>'/>
                    <span class="error-message">
                    <c:if test="${not empty contactValidation.firstNameError}">
                        <c:out value="${contactValidation.firstNameError}"/>
                    </c:if>
                </span>
                </label>
            </div>
            <div>
                <label class="form-label">
                    <span class="form-field">Телефон:</span>
                    <input type="number" class="ml-1 form-control input-sm form-input" name="phone"
                           value='<%=currentContact.getPhone() == null ? "" : currentContact.getPhone() %>'/>
                    <span class="error-message">
                     <c:if test="${not empty contactValidation.phoneError}">
                         <c:out value="${contactValidation.phoneError}"/>
                     </c:if>
                </span>
                </label>
            </div>
            <button type="submit" class="btn btn-primary">Добавить</button>
        </form>
    </table>
</div>
</body>
</html>