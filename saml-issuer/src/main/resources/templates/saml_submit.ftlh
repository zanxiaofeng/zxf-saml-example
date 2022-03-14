<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SAML Token Auto Submit</title>
    <script type="text/javascript">
    var interval = 0;
    var restSeconds = 5;
    function clock()
    {
        restSeconds = restSeconds - 1
        document.getElementById("msg").innerText= "Will auto submit after " + restSeconds + " seconds";
        if (restSeconds == 0){
            document.forms[0].submit();
            window.clearInterval(interval);
        }
    }
    interval = self.setInterval("clock()",1000);
    </script>
</head>
<body>
<h1>UserId: ${userId}</h1>
<h1>OrderId: ${orderId}</h1>
<h1 id="msg">Will auto submit after 5 seconds</h1>
<form action="${targetUrl}" method="POST" target="_blank">
    <input type="hidden" name="saml" value="${saml}">
    <input type="submit">
</form>
</body>
</html>