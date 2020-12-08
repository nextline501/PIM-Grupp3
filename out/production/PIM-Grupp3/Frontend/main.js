let noteClassHolder = [];

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
            noteText: noteText,
            date: Date.now()
        };
    }
    noteClassHolder.push(noteClass);
    sendTextArea();
})

async function sendTextArea(){
    let result = await fetch("/rest/notes", {
        method: "POST",
        body: JSON.stringify(noteClassHolder[0])
    });
    console.log(await result.text());
    noteClassHolder.pop();
}