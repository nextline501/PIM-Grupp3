let noteClassHolder = [];
let notes = [];
//
//This JqQuery grabs the form and and listen for submit
//The value given in the text area is stored in noteClass
//
$("#textAreaForm").submit((e)=>{
    e.preventDefault();
    let noteText = $("#textArea").val();
    console.log(noteText)
    if(noteText == null || noteText == ""){
        alert("Can't add nothing");
    } else{
        noteClass = {
            id: null,
            noteText: noteText
        };
    }
    noteClassHolder.push(noteClass);
    sendTextArea();
})

//
//Here we POST the input to the database. Sending the class via JSON. 
//
async function sendTextArea(){
    let result = await fetch("/rest/notes", {
        method: "POST",
        body: JSON.stringify(noteClassHolder[0])
    });
    console.log(await result.text());
    noteClassHolder.pop();
}

//
//Here we fetch notes that is stored in the database.
//
async function getNotes(){
    let result = await fetch("/rest/notes");
    notes = await result.json();
    renderNotes();
}
async function getImages(){
    let result = await fetch("/rest/images");
    images = await result.json();
    console.log(images);
    //renderImages();
}

function renderNotes(){
    let noteList = $("#notes-list");
    noteList.empty();

    for(note of notes){
        noteList.append(`<ul onclick="loadSelectedNote()" style="width: 160px; float:left; height: 50px; background-color:rgb(113, 158, 173); margin-botton: 10px">
            Note
        </ul>`);
    }
}
/* function renderImages(){
    let imageList = $("#images-list")
    imageList.empty();

    for(image of images){
        imageList.append(`<ul${image.author}${image.url}></ul>`)
    }
} */

function loadSelectedNote(){
    console.log("Slected Note")
}

getNotes();