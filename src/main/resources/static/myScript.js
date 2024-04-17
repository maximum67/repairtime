function getStandardTimeData(markid, modelid, typeEngineid, modificationid, id) {
    fetch('/api/select/grouprepair/'+markid+'/'+modelid+'/'+typeEngineid+'/'+modificationid+'/'+id,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then((data) => {
            // Process the fetched data and update the HTML form
            const htmlFormType = document.querySelector('#typeRepair');
            const htmlFormTime = document.querySelector('#timeRepair');
            const typeRepairHead = document.getElementById('typeRepairHead');
            const timeRepairHead = document.getElementById('timeRepairHead');
             typeRepairHead.textContent = "___________Вид ремонта____________";
             timeRepairHead.textContent = "Нормо-часы";
            while(htmlFormType.firstChild){
                htmlFormType.removeChild(htmlFormType.firstChild);
            };
            while(htmlFormTime.firstChild){
                 htmlFormTime.removeChild(htmlFormTime.firstChild);
            };

            data.forEach(item => {
                // Assuming each item has 'id' and 'name' properties
                const typeRepair = document.createElement('tr');
                const timeRepair = document.createElement('tr');
                typeRepair.textContent = item.key;
                timeRepair.textContent = item.value;
               htmlFormType.appendChild(typeRepair);
               htmlFormTime.appendChild(timeRepair);
            });
        })
        .catch(error => {
        alert(error);
            console.error('There was a problem with the fetch operation:', error);
        });
}