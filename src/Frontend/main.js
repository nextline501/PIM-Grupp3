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
    deleteNote()
    noteClassHolder.push(noteClass);
    sendTextArea();
    location.reload();
});

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
    noteList = $("#notes-list");
    noteList.empty();

    for(note of notes){
        noteList.append(`<ul id="noteItem" style="width: 160px; float:left; height: 50px; background-color:rgb(113, 158, 173); margin-botton: 10px">
            ${note.noteText}
        </ul>`);
    }
    loadSelectedNote();
}
/* function renderImages(){
    let imageList = $("#images-list")
    imageList.empty();

    for(image of images){
        imageList.append(`<ul${image.author}${image.url}></ul>`)
    }
} */

function loadSelectedNote(){
    let noteItemList = $("ul");
    console.log(noteItemList.length);

    for(let i = 0; i < noteItemList.length; i++){
        $(noteItemList[i]).click(()=>{
            $("#textArea").val(`${notes[i].noteText}`)
        })
    }
}

async function deleteNote(note){
    await fetch("/rest/delete", {
        method: "POST",
        body: JSON.stringify(note)
    });
}


$("#textArea").val('')
getNotes();