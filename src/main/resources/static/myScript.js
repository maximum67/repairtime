function getStandardTimeData(markid, modelid, typeEngineid, modificationid, id) {

    fetch('/api/select/grouprepair/'+markid+'/'+modelid+'/'+typeEngineid+'/'+modificationid+'/'+id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Process the fetched data and update the HTML form
            const htmlForm = document.querySelector('#myForm');
            data.forEach(item => {
                // Assuming each item has 'id' and 'name' properties
                const option = document.createElement('option');
                option.value = item.id;
                option.text = item.name;
                alert(option.value);
//                htmlForm.appendChild(option);
            });
        })
        .catch(error => {
        alert(error);
            console.error('There was a problem with the fetch operation:', error);
        });
}