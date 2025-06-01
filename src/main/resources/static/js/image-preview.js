/* ===== Funcionalidad que permite poder previsualizar la imagen que el usuario escoge en el input file === */

/*
* es necesario que el input file para nuevo y editar tenga la clase 'file-input' y tener una etiqueta
* img para nuevo y editar con la clase 'img-preview'
*/
document.addEventListener('change', function(e) {
    if (e.target.classList.contains('file-input')) {
        const container = e.target.closest('.flex-col');
        const imgPreview = container.querySelector('.img-preview');
        const fotoActualInput = container.querySelector('[name="fotoActual"]'); // Campo oculto
        const defaultFile = "../images/user.png";

        if (e.target.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                imgPreview.src = e.target.result;
            };
            reader.readAsDataURL(e.target.files[0]);
        } else {
            imgPreview.src = fotoActualInput?.value
                ? `/fotos/docente/${fotoActualInput.value}`
                : defaultFile;
        }
    }
});