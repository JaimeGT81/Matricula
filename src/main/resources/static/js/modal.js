function openUserModal(btn) {
    const userId = btn.dataset.userid;
    const email = btn.dataset.useremail;
    const role = btn.dataset.role;

    // Get modal and form elements
    const modal = document.querySelector('#modalUser');
    const titleElement = modal.querySelector('h2');
    const userIdInput = modal.querySelector('#userId');
    const emailInput = modal.querySelector('#userEmail');
    const roleSelect = modal.querySelector('#selectedRole');

    // Set values if editing
    if (userId) {
        userIdInput.value = userId;
        emailInput.value = email;
        if (roleSelect) {
            Array.from(roleSelect.options).forEach(option => {
                option.selected = option.value === role;
            });
        }
        titleElement.textContent = 'Editar Usuario';
    } else {
        // Clear form for new user
        userIdInput.value = '';
        emailInput.value = '';
        if (roleSelect) {
            roleSelect.selectedIndex = 0;
        }
        titleElement.textContent = 'Crear Usuario';
    }

    // Show modal
    modal.classList.remove('hidden');
}

// Add event listeners for modal close buttons
document.addEventListener('DOMContentLoaded', function() {
    // Close modal when clicking close button
    document.querySelectorAll('[data-modal-close]').forEach(button => {
        button.addEventListener('click', function() {
            const modalId = this.getAttribute('data-modal-close');
            document.querySelector(modalId).classList.add('hidden');
        });
    });

    // Close modal when clicking outside
    document.querySelectorAll('[id^="modal"]').forEach(modal => {
        modal.addEventListener('click', function(e) {
            if (e.target === this) {
                this.classList.add('hidden');
            }
        });
    });

    // Open modal when clicking new user button
    document.querySelectorAll('[data-modal-open]').forEach(button => {
        button.addEventListener('click', function() {
            const modalId = this.getAttribute('data-modal-open');
            if (!this.hasAttribute('data-userid')) {
                // It's the "Nuevo Usuario" button
                openUserModal({
                    dataset: {
                        userid: null,
                        useremail: '',
                        role: ''
                    }
                });
            }
        });
    });
});