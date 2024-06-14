function toPDF () {
    html2pdf()
       .set({
            margin: 1,
            filename: "specification.pdf",
            image: { type: "jpeg", quality: 0.95 },
            enableLinks : true,
           jsPDF: { format: "A4", orientation: "portrait" }
        })
        .from(document.getElementById("specification"))
        .save();
}