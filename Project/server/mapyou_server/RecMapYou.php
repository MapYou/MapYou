<em><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Forgot password MapMe </title>

<style type="text/css">
.cix {
	font-family: Verdana, Geneva, sans-serif;
	font-size:20px;
}
.titlecix {
	font-family: Verdana, Geneva, sans-serif;
	font-size: 20px;
	alignment-adjust: central;
	text-align:center;
}
</style>
<h2 class="titlecix"  > Welcome in the Password Recovery MapYou </h2>
</head>
<body bgcolor="#66FF99">
<body>
<?php
 $user=$_GET['nickname'];
  
?>

<div id="central" style="position: center; margin: auto; padding-top: 50px; width: 300px; text-align: left;">

<form action="http://www.mapyou.altervista.org/myMapYou/Controller/passwordUpdate.php" method="post" name="forgot" id="form" target="_blank">

<table width=300" border="5" align="center" bordercolor="#008800" bgcolor="#ADFF2F" cellpadding="10"  >
 <tr>
    <td width="192"  align="center"><h3 class="cix"> Create your Password  </h3>
</td>
  </tr>
  <tr>
    <td  align="center"> <input name="pass" type="password" placeholder="password" size="25" \ />
</td>
  </tr>
  <tr>
    <td  align="center"><input name="confirmpassword" type="password" placeholder="confirm password"  size="25"  /></td>
  </tr>
    <tr>
        
    <td align="center"><input type="button" onClick="verify()"  value="New Password" size="25" />
    <input type='hidden' name="nickname" value="<?php echo $user; ?>" />
    <input name="reset" type="reset" value="Reset" size="25" /></td>
   
  </tr>
</table>
</form>
</div> 


<script type="text/javascript">
function verify()
{
var x= document.getElementById("form");
var p= x.elements[0].value;
var c= x.elements[1].value;
if(p!=c){
	window.alert("Password doesn't match!");
}else if(p=="" || c==""){
	window.alert("Password doesn't empty!");
}
else
	x.submit();   
}
</script>

</body>
</html>
</em>
 
 