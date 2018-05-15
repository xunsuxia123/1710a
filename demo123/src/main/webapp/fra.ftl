<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type='text/css'>
    *{
        margin: 0;
        padding:0;
    }
    html{
        font-size: 14px;
    }
    p{
        width: 100%;
        margin-bottom: 5px;
    }
    p img{
        width: 100%;

    }
</style>
<!DOCTYPE html>
<html>
<body>
<h4>freemaker</h4>

</br>
<form id="WordForm">
    <input type="hidden" name="newsid" value="${news.newsid}">
    <input type="hidden" name="newsname" value="${news.newsname}">
    <input type="hidden" name="newsintr" value="${news.newsintr}">
${news.newsintr}
</form>

</br>

</body>
</html>