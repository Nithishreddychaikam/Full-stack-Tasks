let students = [

{name:"Arjun", department:"CSE", date:"2023-01-15"},
{name:"Meena", department:"ECE", date:"2022-12-20"},
{name:"Rahul", department:"CSE", date:"2023-02-10"},
{name:"Kiran", department:"MECH", date:"2021-11-25"},
{name:"Divya", department:"ECE", date:"2023-03-05"}

];

displayData(students);

function displayData(data)
{
let table = document.getElementById("tableBody");
table.innerHTML="";

data.forEach(student => {

let row = `<tr>
<td>${student.name}</td>
<td>${student.department}</td>
<td>${student.date}</td>
</tr>`;

table.innerHTML += row;

});

countDepartments(data);
}

function sortByName()
{
students.sort((a,b)=> a.name.localeCompare(b.name));
displayData(students);
}

function sortByDate()
{
students.sort((a,b)=> new Date(a.date) - new Date(b.date));
displayData(students);
}

function filterDepartment()
{
let dept = document.getElementById("departmentFilter").value;

if(dept === "All")
{
displayData(students);
}
else
{
let filtered = students.filter(s => s.department === dept);
displayData(filtered);
}
}

function countDepartments(data)
{
let counts = {};

data.forEach(s => {
counts[s.department] = (counts[s.department] || 0) + 1;
});

let result = "";

for(let dept in counts)
{
result += dept + ": " + counts[dept] + " students <br>";
}

document.getElementById("deptCount").innerHTML = result;
}