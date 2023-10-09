$(document).ready(() => {
    let currentPage = 0;
    const pageSize = 10;
    let sortBy = 'mpxn';
    let direction = 'asc';
    let currentSearch = '';

    function populateTable(page, search='', sortBy='mpxn', direction='asc') {
        const token = localStorage.getItem('access_token');

        let url = `/tabledata?page=${page}&size=${pageSize}&sortBy=${sortBy}&direction=${direction}`;

        if (search){
            url = `/search?page=${page}&size=${pageSize}&sortBy=${sortBy}&direction=${direction}&search=${search}`;
        }

        $.ajax({
            method: 'GET',
            url: url,
            headers: {
                "Authorization": "Bearer " + token
            },
            dataType: 'json',
            success: function (data) {
                const tableBody = $('#table-body');
                tableBody.empty();
                data.devices.forEach(item => {
                    item.devices.forEach(device => {
                        const row = `
                    <tr>
                        <td>${item.mpxn}</td>
                        <td>${device.deviceId}</td>
                        <td>${device.deviceType}</td>
                        <td>${device.deviceStatus}</td>
                        <td>${device.deviceModel}</td>
                        <td>${device.deviceManufacturer}</td>
                        <td>${device.deviceFirmwareVersion}</td>
                        <td>${device.deviceFirmwareVersionStatus}</td>
                        <td>${item.postcode}</td>
                    </tr> `;
                        tableBody.append(row);
                    });
                });
                // update current page
                currentPage = page;

                // update pagination buttons
                updatePaginationButtons(data.totalPages);
            },
            error: function (error) {
                console.error('Error fetching data:', error);
            }
        });
    }

    // Pagination Buttons
    function updatePaginationButtons(totalPages) {
        const paginationButtons = $('#pagination-buttons');
        paginationButtons.empty();
        const maxPagesDisplayed = 10;
        const startPage = Math.max(currentPage - Math.floor(maxPagesDisplayed / 2), 0);
        const lastPage = Math.min(startPage + maxPagesDisplayed, totalPages);

        // previous button
        if (currentPage > 0) {
            const prevButton = $('<button id="previous-button">').text('Previous');
            paginationButtons.append(prevButton);
        }

        // first button - always visible
        const firstPageButton = $(`<button class="${currentPage === 0 ? 'active' : ''} page-button" data-page="0">`).text(1);
        firstPageButton.on('click', () => {
            currentPage = 0;
            populateTable(currentPage, currentSearch, sortBy, direction);
        });
        paginationButtons.append(firstPageButton);

        // previous ellipsis if needed
        if (startPage > 1) {
            const setPrevButton = $('<button>').text('...');
            setPrevButton.on('click', () => {
                currentPage = Math.max(0, currentPage - 5);
                populateTable(currentPage, currentSearch, sortBy, direction);
            })
            paginationButtons.append(setPrevButton);
        }

        // numbered page buttons
        for (let i = startPage; i < lastPage; i++) {
            // skip first and last page if already added
            if (i === 0 || i === totalPages - 1) continue;
            const pageNumber = i + 1;
            const activeClass = i === currentPage ? 'active' : '';
            const pageButton = $(`<button class="${activeClass} page-button" data-page="${i}">`).text(pageNumber);
            pageButton.on('click', () => {
                currentPage = i;
                populateTable(currentPage, currentSearch, sortBy, direction);
            });
            paginationButtons.append(pageButton);
        }

        // next ellipsis if needed
        if (lastPage < totalPages - 1) {
            const setNextButton = $('<button>').text('...');
            setNextButton.on('click', () => {
                currentPage = Math.min(totalPages - 2, lastPage);
                populateTable(currentPage, currentSearch, sortBy, direction);
            });
            paginationButtons.append(setNextButton);
        }

        // last page button - always visible
        if (totalPages > 1) {
            const lastPageButton = $(`<button class="${currentPage === totalPages - 1 ? 'active' : ''} page-button" data-page="${totalPages - 1}">`).text(totalPages);
            lastPageButton.on('click', () => {
                currentPage = totalPages - 1;
                populateTable(currentPage, currentSearch, sortBy, direction);
            });
            paginationButtons.append(lastPageButton)
        }

        // next button
        if (currentPage < totalPages - 1) {
            const nextButton = $('<button id="next-button">').text('Next');
            paginationButtons.append(nextButton);
        }
    }

    $('#pagination-buttons').on('click', '#previous-button', function () {
        previousPage();
    });

    $('#pagination-buttons').on('click', '#next-button', function () {
        nextPage();
    });

    // previous button function
    function previousPage() {
        if (currentPage > 0) {
            currentPage--;
            populateTable(currentPage, currentSearch, sortBy, direction);
        }
    }

    // next button function
    function nextPage() {
        currentPage++;
        populateTable(currentPage, currentSearch, sortBy, direction);
    }

    // sort buttons arrows
    $('.sort-header').on('click', function() {
        $('.sort-header').each(function() {
            // set both headers to default (asc) order
            const header = $(this).text().replace(' ▲', '').replace(' ▼', '');
            $(this).text(header + ' ▲');
        });

        sortBy = $(this).data('sortby'); // update sortBy value from column data attribute
        direction = direction === 'asc' ? 'desc' : 'asc'; // flip direction each time a sort button is clicked

        const header = $(this).text().replace(' ▲', '').replace(' ▼', ''); // update arrow in the clicked header
        $(this).text(header + (direction === 'asc' ? ' ▲' : ' ▼'));
        populateTable(0, currentSearch, sortBy, direction);
    });

    // search button
    $('#searchButton').click(function() {
        currentSearch = $('#search-input').val();
        populateTable(0, currentSearch, sortBy, direction);
    });
    // submit search via Enter key
    $('#search-input').on('keypress', function(key) {
        if (key.which === 13){ // enter key
            key.preventDefault();
            $('#searchButton').click();
        }
    });
    // clear search results
    $('#clearButton').click(function() {
        currentSearch = ''
        $('#search-input').val('')
        populateTable(0, currentSearch, sortBy, direction);
    });

    // initial data population
    populateTable(currentPage, currentSearch, sortBy, direction);
});