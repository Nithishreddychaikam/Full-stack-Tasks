function validateName(event)
{
let char = String.fromCharCode(event.which);

if(!/[a-zA-Z ]/.test(char))
{
alert("Only letters allowed in Name");
event.preventDefault();
}
}

function validateEmail(event)
{
let value = document.getElementById("email").value;

if(value.includes(" "))
{
alert("Email should not contain spaces");
event.preventDefault();
}
}

function submitForm()
{
let name = document.getElementById("name").value;
let email = document.getElementById("email").value;
let feedback = document.getElementById("feedback").value;

if(name=="" || email=="" || feedback=="")
{
alert("Please fill all fields");
}
else
{
alert("Thank you! Your feedback has been submitted.");
}
}