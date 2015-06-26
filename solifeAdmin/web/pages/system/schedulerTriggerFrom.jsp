<%@ include file="/common/taglibs.jsp"%>
<html>
    <head>
        <title><fmt:message key="menu.viewSchedulerMgr"/></title>
        <script type="text/javascript">
        function chooseEasyCron()
        {
            $('cronExpression').value = $('easyCron').value;
        }
        </script>
    </head>
    <body>
        
        <form class="mainForm" method="post">

            <content tag="buttons">
            <ul>
                <li>
                    <cartmatic:cartmaticBtn btnType="save" onclick="fnDoSave(this,'triggerName');" />
                </li>
                <li>
                    <cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='schedulers.html'" />
                </li>
            </ul>
            </content>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-content">
                            <tr>
                               <td class="FieldLabel">
                                    <fmt:message key="scheduler.taskName"/>
                                </td>
                                <td>
                                    ${trigger.name}
                                    <input type="hidden" id="triggerName" value="${trigger.name}">
                                </td>
                            </tr>
                            <tr>
                                <td class="FieldLabel">
                                    <fmt:message key="scheduler.desc"/>
                                </td>
                                <td>
                                    ${trigger.description}
                                </td>
                            </tr>
                            <c:if test="${triggerType == 'CronTrigger'}">
                                <tr>
                                    <td class="FieldLabel">
                                        <fmt:message key="scheduler.expression"/>
                                    </td>
                                    <td>
                                        <input name="cronExpression" id="cronExpression" class="Field400"
                                            type="text" value="${trigger.cronExpression}" />
                                        <select name="easyCron" id="easyCron" onChange="chooseEasyCron()" class="Field400">
                                            <option value="${trigger.cronExpression}">
                                                <fmt:message key="scheduler.chooseExpression"/>
                                            </option>
                                            <option value="0 * * * * ?">
                                                <fmt:message key="scheduler.chooseExpression1"/>
                                            </option>
                                            <option value="0 0/5 * * * ?">
                                                <fmt:message key="scheduler.chooseExpression2"/>
                                            </option>
                                            <option value="0 0/15 * * * ?">
                                                <fmt:message key="scheduler.chooseExpression3"/>
                                            </option>
                                            <option value="0 0/30 * * * ?">
                                                <fmt:message key="scheduler.chooseExpression4"/>
                                            </option>
                                            <option value="0 0 * * * ?">
                                                <fmt:message key="scheduler.chooseExpression5"/>
                                            </option>
                                            <option value="0 0 0/2 * * ?">
                                                <fmt:message key="scheduler.chooseExpression6"/>
                                            </option>
                                            <option value="0 0 0/4 * * ?">
                                                <fmt:message key="scheduler.chooseExpression7"/>
                                            </option>
                                            <option value="0 0 0,12 * * ?">
                                                <fmt:message key="scheduler.chooseExpression8"/>
                                            </option>
                                            <option value="0 0 8 * * ?">
                                                <fmt:message key="scheduler.chooseExpression9"/>
                                            </option>
                                            <option value="0 0 2 ? * MON">
                                                <fmt:message key="scheduler.chooseExpression10"/>
                                            </option>
                                            <option value="0 0 2 ? * FRI">
                                                <fmt:message key="scheduler.chooseExpression11"/>
                                            </option>
                                            <option value="0 0 2 ? * MON-FRI">
                                                <fmt:message key="scheduler.chooseExpression12"/>
                                            </option>
                                            <option value="0 0 2 1 * ?">
                                                <fmt:message key="scheduler.chooseExpression13"/>
                                            </option>
                                            <option value="0 0 2 L * ?">
                                               <fmt:message key="scheduler.chooseExpression14"/>
                                            </option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="FieldLabel">

                                    </td>
                                    <td>
                                        <b><fmt:message key="scheduler.ExpressionReference"/></b>
                                        <table cellspacing="0">
                                            <tr>
                                                <td align="left" width="20%">
                                                    Expression
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    Meaning
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 0 12 * * ?"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 12pm (noon) every day
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 15 10 ? * *"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 10:15am every day
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 15 10 * * ?"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 10:15am every day
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 15 10 * * ? *"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 10:15am every day
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 15 10 * * ? 2005"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 10:15am every day during the year 2005
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 * 14 * * ?"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire every minute starting at 2pm and ending at 2:59pm, every
                                                        day
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 0/5 14 * * ?"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire every 5 minutes starting at 2pm and ending at 2:55pm, every
                                                        day
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 0/5 14,18 * * ?"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire every 5 minutes starting at 2pm and ending at 2:55pm, AND
                                                        fire every 5 minutes starting at 6pm and ending at 6:55pm, every
                                                        day
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 0-5 14 * * ?"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire every minute starting at 2pm and ending at 2:05pm, every
                                                        day
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 10,44 14 ? 3 WED"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 2:10pm and at 2:44pm every Wednesday in the month of
                                                        March.
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 15 10 ? * MON-FRI"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 10:15am every Monday, Tuesday, Wednesday, Thursday and
                                                        Friday
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 15 10 15 * ?"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 10:15am on the 15th day of every month
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 15 10 L * ?"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 10:15am on the last day of every month
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 15 10 ? * 6L"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 10:15am on the last Friday of every month
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 15 10 ? * 6L"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 10:15am on the last Friday of every month
                                                    </code>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left">
                                                    <code>
                                                        "0 15 10 ? * 6#3"
                                                    </code>
                                                </td>
                                                <td align="left">&nbsp;
                                                    
                                                </td>
                                                <td align="left">
                                                    <code>
                                                        Fire at 10:15am on the third Friday of every month
                                                    </code>
                                                </td>
                                            </tr>

                                        </table>

                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${triggerType == 'SimpleTrigger'}">
                                <tr>
                                    <td class="FieldLabel">
                                        <fmt:message key="scheduler.ExpressionInterval"/>
                                    </td>
                                    <td>
                                        <input name="repeatInterval" id="repeatInterval" class="Field400"
                                            type="text" value="${trigger.repeatInterval}" />
                                        （<fmt:message key="scheduler.ExpressionUnit"/>）
                                    </td>
                                </tr>
                            </c:if>
            </table>
            <input type="hidden" name="trigger" value="${param.trigger}" />
            <input type="hidden" name="group" value="${param.group}" />
            <input type="hidden" name="triggerType" value="${triggerType}" />
        </form>
    </body>
</html>
