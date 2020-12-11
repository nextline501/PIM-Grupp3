//This array holds the Note class that is sent to the server, this array allways contains just one class and is poped after the object is sent.
//ORM
let noteClassHolder = [];

let imgClassHolder = [];

//This array hold all our notes the was sent from the db
let notes = [];

//This variable hold the current id for the selected note.
let currentNoteId;

//
//This JqQuery grabs the form and and listen for submit
//The value given in the text area is stored in noteClass and is sent and creates a new Note in the db
//
$("#textAreaForm").submit((e)=>{
    e.preventDefault();
    let noteText = $("#textArea").val();
    let randomId = (Math.random()*1000000);
    console.log(noteText)
    if(noteText == null || noteText == ""){
        alert("Can't add nothing");
    } else{
        noteClass = {
            id: randomId,
            noteText: noteText
        };
    }
    noteClassHolder.push(noteClass);
    sendTextArea(randomId);
});

//
//Here we POST the input to the database. Sending the class via JSON.
//
async function sendTextArea(idParam){
    console.log("hit 1")
    await fetch("/rest/create-post", {
        method: "POST",
        body: JSON.stringify(noteClassHolder[0])
    });
    
    console.log("hit 2")
    let files = document.querySelector('#imgFile').files;
    let formData = new FormData();
    console.log("hit 3")
    for(let file of files){
        formData.append('filesKek', file, file.name);
    }
    console.log("hit 4")
    let uploadResult = await fetch('/api/file-upload', {
        method: "POST",
        body: formData
    });
    console.log("hit 5")
    let imgUrl = await uploadResult.text(); //get the url back from server side

    console.log("note id before sending: " + idParam);
    let imgUrlClass = {
        id: null,
        url: imgUrl,
        notes_id: idParam
    }
    console.log("hit 6")
    let imgResult = await fetch("/rest/imgUrl", {
        method: "POST",
        body: JSON.stringify(imgUrlClass)
    });
    console.log("hit 7")
    console.log(await imgResult.text());
    noteClassHolder.pop();
    location.reload();
}

//
//Here we fetch notes that is stored in the database.
//
async function getNotes(){
    let result = await fetch("/rest/notes");
    notes = await result.json();
    renderNotes();
}

//
//This function renders availble notes that is stored in the db
//noteList is a section in the html diagram and is dynamicly adding <ul> to the list
//
function renderNotes(){
    let noteList = $("#notes-list");
    noteList.empty();
    
    for(note of notes){
        noteList.append(`<ul id="noteItem" style="width: 160px; float:left; height: 50px; background-color:rgb(113, 158, 173); margin-botton: 10px">
            ${note.noteText}
        </ul>`);
    }
    loadSelectedNote();
}

//
//This function listens for a click and insert the text value in to the editor.
//In order to know what note we are dealing with we are also storing the id of the selected note in the var: currentNoteId
//
function loadSelectedNote(){
    let noteItemList = $("ul");
    console.log(noteItemList.length);

    for(let i = 0; i < noteItemList.length; i++){
        $(noteItemList[i]).click(()=>{
            currentNoteId = i;
            $("#textArea").val(`${notes[i].noteText}`)
        })
    }
}


//
//Update button stores the id and calls updateNote() function
//
$("#update").click((e)=>{
    e.preventDefault();
    let updateText = $("#textArea").val();

    noteClass = {
        id: notes[currentNoteId].id,
        noteText: updateText
    };

    noteClassHolder.push(noteClass); 
    updateNote();
    uploadImg();
});

//
//Here we post a JSON to server to Update db
//
async function updateNote(){
    await fetch("/rest/update", {
        method: "POST",
        body: JSON.stringify(noteClassHolder[0])
    });
    noteClassHolder.pop();
}

//
//Delete button stores the id and calls deleteNote() function
//
$("#delete").click((e)=>{
    e.preventDefault();
    noteClass = {
        id: notes[currentNoteId].id,
        noteText: null
    }
    noteClassHolder.push(noteClass);
    deleteNote();
    location.reload();
});

//
//deleteNote function sends the id for the note that is up for delete
//
async function deleteNote(){
    await fetch("/rest/delete", {
        method: "POST",
        body: JSON.stringify(noteClassHolder[0])
    });
    noteClassHolder.pop();
}

//if we reload textArea is cleared.
$("#textArea").val('')
getNotes();