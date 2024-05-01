function getRepairGroup(markid, modelid, typeEngineid, modificationid, id, groupMainName) {
    fetch('/api/select/groupRepair/'+markid+'/'+modelid+'/'+typeEngineid+'/'+modificationid+'/'+id,{
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
            const htmlFormTypeR = document.querySelector('#typeRepair');
            const htmlFormTimeR = document.querySelector('#timeRepair');
            const typeRepairHead = document.getElementById('typeRepairHead');
            const timeRepairHead = document.getElementById('timeRepairHead');
            typeRepairHead.textContent = "";
            timeRepairHead.textContent = "";
            while(htmlFormTypeR.firstChild){
                htmlFormTypeR.removeChild(htmlFormTypeR.firstChild);
            }
            while(htmlFormTimeR.firstChild){
                htmlFormTimeR.removeChild(htmlFormTimeR.firstChild);
            }
            const htmlFormType1 = document.querySelector('#repairGroup');
            const repairGroupHead = document.getElementById('repairGroupHead');
             repairGroupHead.textContent = groupMainName;
            while(htmlFormType1.firstChild){
                htmlFormType1.removeChild(htmlFormType1.firstChild);
            }
            data.forEach(item => {
                const repairGroup = document.createElement('a');
                const br = document.createElement('br');
                repairGroup.textContent = item.key;
                repairGroup.href = '#';
                repairGroup.id = item.key;
                let repairGroupId = item.value;
                let repairGroupName = item.key;
               htmlFormType1.appendChild(repairGroup);
               htmlFormType1.appendChild(br);
                repairGroup.setAttribute("onclick", "getStandardTimeData("+modificationid+","+repairGroupId+","+"'"+repairGroupName+"')");
            });
        })
        .catch(error => {
        alert(error);
            console.error('There was a problem with the fetch operation:', error);
        });
}
function getStandardTimeData(modificationid, id, repairGroupName) {
    fetch('/api/select/standardTime/1/2/3/'+modificationid+'/'+id, {
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
            typeRepairHead.textContent = repairGroupName;
            timeRepairHead.textContent = "Нормо-часы";
            while(htmlFormType.firstChild){
                htmlFormType.removeChild(htmlFormType.firstChild);
            }
            while(htmlFormTime.firstChild){
                htmlFormTime.removeChild(htmlFormTime.firstChild);
            }
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