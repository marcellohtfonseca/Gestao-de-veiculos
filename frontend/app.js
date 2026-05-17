const API = 'http://localhost:8080/api';

// ─── Estado de paginação ───────────────────────────────────────────────────
let currentPage = 0;
let totalPages  = 1;

// ─── Cache de marcas ───────────────────────────────────────────────────────
let marcasCache = [];

// ══════════════════════════════════════════════════════════════════════════════
// Navegação
// ══════════════════════════════════════════════════════════════════════════════
function showSection(id, btn) {
    document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
    document.querySelectorAll('nav button').forEach(b => b.classList.remove('active'));
    document.getElementById(id).classList.add('active');
    btn.classList.add('active');

    if (id === 'veiculos') loadVeiculos(0);
    if (id === 'modelos')  loadModelos();
    if (id === 'marcas')   loadMarcas();
}

// ══════════════════════════════════════════════════════════════════════════════
// Utilitários
// ══════════════════════════════════════════════════════════════════════════════
function fmt(value) {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
}

function showToast(msg, type = '') {
    const t = document.getElementById('toast');
    t.textContent = msg;
    t.className = `toast show ${type}`;
    setTimeout(() => t.classList.remove('show'), 3000);
}

function closeModal(id) {
    document.getElementById(id).classList.remove('open');
}

async function apiFetch(url, options = {}) {
    const res = await fetch(API + url, {
        headers: { 'Content-Type': 'application/json' },
        ...options
    });
    if (!res.ok) {
        const err = await res.json().catch(() => ({ message: res.statusText }));
        throw new Error(err.message || 'Erro na requisição');
    }
    if (res.status === 204) return null;
    return res.json();
}

// ══════════════════════════════════════════════════════════════════════════════
// MARCAS
// ══════════════════════════════════════════════════════════════════════════════
async function loadMarcas() {
    const data = await apiFetch('/marcas');
    marcasCache = data;
    renderMarcas(data);
    populateMarcaSelects(data);
}

function renderMarcas(list) {
    const tbody = document.getElementById('marcas-tbody');
    tbody.innerHTML = list.map(m => `
        <tr>
            <td>${m.id}</td>
            <td>${m.nome}</td>
            <td>${m.paisOrigem}</td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="openMarcaModal(${m.id})">Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteMarca(${m.id})">Excluir</button>
            </td>
        </tr>`).join('');
}

function populateMarcaSelects(list) {
    const selects = ['f-marca', 'v-marca', 'm-marca'];
    selects.forEach(id => {
        const el = document.getElementById(id);
        if (!el) return;
        const first = el.options[0];
        el.innerHTML = '';
        el.appendChild(first);
        list.forEach(m => {
            const opt = document.createElement('option');
            opt.value = m.id;
            opt.textContent = m.nome;
            el.appendChild(opt);
        });
    });
}

function openMarcaModal(id = null) {
    document.getElementById('ma-id').value   = id || '';
    document.getElementById('ma-nome').value = '';
    document.getElementById('ma-pais').value = '';
    document.getElementById('modal-marca-title').textContent = id ? 'Editar Marca' : 'Nova Marca';

    if (id) {
        const m = marcasCache.find(x => x.id === id);
        if (m) {
            document.getElementById('ma-nome').value = m.nome;
            document.getElementById('ma-pais').value = m.paisOrigem;
        }
    }
    document.getElementById('modal-marca').classList.add('open');
}

async function saveMarca() {
    const id   = document.getElementById('ma-id').value;
    const body = {
        nome:       document.getElementById('ma-nome').value.trim(),
        paisOrigem: document.getElementById('ma-pais').value.trim()
    };
    if (!body.nome || !body.paisOrigem) { showToast('Preencha todos os campos.', 'error'); return; }

    try {
        if (id) {
            await apiFetch(`/marcas/${id}`, { method: 'PUT', body: JSON.stringify(body) });
            showToast('Marca atualizada.', 'success');
        } else {
            await apiFetch('/marcas', { method: 'POST', body: JSON.stringify(body) });
            showToast('Marca cadastrada.', 'success');
        }
        closeModal('modal-marca');
        loadMarcas();
    } catch (e) { showToast(e.message, 'error'); }
}

async function deleteMarca(id) {
    if (!confirm('Excluir esta marca?')) return;
    try {
        await apiFetch(`/marcas/${id}`, { method: 'DELETE' });
        showToast('Marca removida.', 'success');
        loadMarcas();
    } catch (e) { showToast(e.message, 'error'); }
}

// ══════════════════════════════════════════════════════════════════════════════
// MODELOS
// ══════════════════════════════════════════════════════════════════════════════
let modelosCache = [];

async function loadModelos() {
    const data = await apiFetch('/modelos');
    modelosCache = data;
    renderModelos(data);
}

function renderModelos(list) {
    const tbody = document.getElementById('modelos-tbody');
    tbody.innerHTML = list.map(m => `
        <tr>
            <td>${m.id}</td>
            <td>${m.nome}</td>
            <td>${m.categoria}</td>
            <td>${m.marcaNome}</td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="openModeloModal(${m.id})">Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteModelo(${m.id})">Excluir</button>
            </td>
        </tr>`).join('');
}

function openModeloModal(id = null) {
    document.getElementById('m-id').value        = id || '';
    document.getElementById('m-nome').value      = '';
    document.getElementById('m-categoria').value = '';
    document.getElementById('modal-modelo-title').textContent = id ? 'Editar Modelo' : 'Novo Modelo';

    if (marcasCache.length === 0) loadMarcas();
    else populateMarcaSelects(marcasCache);

    if (id) {
        const m = modelosCache.find(x => x.id === id);
        if (m) {
            document.getElementById('m-nome').value      = m.nome;
            document.getElementById('m-categoria').value = m.categoria;
            document.getElementById('m-marca').value     = m.marcaId;
        }
    }
    document.getElementById('modal-modelo').classList.add('open');
}

async function saveModelo() {
    const id   = document.getElementById('m-id').value;
    const body = {
        nome:      document.getElementById('m-nome').value.trim(),
        categoria: document.getElementById('m-categoria').value.trim(),
        marcaId:   parseInt(document.getElementById('m-marca').value)
    };
    if (!body.nome || !body.categoria || !body.marcaId) { showToast('Preencha todos os campos.', 'error'); return; }

    try {
        if (id) {
            await apiFetch(`/modelos/${id}`, { method: 'PUT', body: JSON.stringify(body) });
            showToast('Modelo atualizado.', 'success');
        } else {
            await apiFetch('/modelos', { method: 'POST', body: JSON.stringify(body) });
            showToast('Modelo cadastrado.', 'success');
        }
        closeModal('modal-modelo');
        loadModelos();
    } catch (e) { showToast(e.message, 'error'); }
}

async function deleteModelo(id) {
    if (!confirm('Excluir este modelo?')) return;
    try {
        await apiFetch(`/modelos/${id}`, { method: 'DELETE' });
        showToast('Modelo removido.', 'success');
        loadModelos();
    } catch (e) { showToast(e.message, 'error'); }
}

// ══════════════════════════════════════════════════════════════════════════════
// VEÍCULOS
// ══════════════════════════════════════════════════════════════════════════════
async function loadVeiculos(page = 0) {
    currentPage = page;
    const params = new URLSearchParams({ page, size: 10, sort: 'id' });

    const marcaId  = document.getElementById('f-marca').value;
    const modeloId = document.getElementById('f-modelo').value;
    const precoMin = document.getElementById('f-precoMin').value;
    const precoMax = document.getElementById('f-precoMax').value;
    const anoMin   = document.getElementById('f-anoMin').value;
    const anoMax   = document.getElementById('f-anoMax').value;
    const status   = document.getElementById('f-status').value;

    if (marcaId)  params.append('marcaId',  marcaId);
    if (modeloId) params.append('modeloId', modeloId);
    if (precoMin) params.append('precoMin', precoMin);
    if (precoMax) params.append('precoMax', precoMax);
    if (anoMin)   params.append('anoMin',   anoMin);
    if (anoMax)   params.append('anoMax',   anoMax);
    if (status)   params.append('status',   status);

    try {
        const data = await apiFetch(`/veiculos?${params}`);
        totalPages = data.totalPages;
        renderVeiculos(data.content);
        document.getElementById('page-info').textContent =
            `Página ${data.number + 1} de ${data.totalPages} (${data.totalElements} registros)`;
        document.getElementById('btn-prev').disabled = data.first;
        document.getElementById('btn-next').disabled = data.last;
    } catch (e) { showToast(e.message, 'error'); }
}

function renderVeiculos(list) {
    const tbody = document.getElementById('veiculos-tbody');
    if (!list.length) {
        tbody.innerHTML = '<tr><td colspan="10" style="text-align:center;padding:20px;color:#999">Nenhum veículo encontrado.</td></tr>';
        return;
    }
    tbody.innerHTML = list.map(v => `
        <tr>
            <td>${v.id}</td>
            <td><strong>${v.placa}</strong></td>
            <td>${v.marcaNome}</td>
            <td>${v.modeloNome}</td>
            <td>${v.cor}</td>
            <td>${v.ano}</td>
            <td>${fmt(v.preco)}</td>
            <td>${v.quilometragem.toLocaleString('pt-BR')} km</td>
            <td><span class="badge badge-${v.status}">${v.status}</span></td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="openVeiculoModal(${v.id})">Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteVeiculo(${v.id})">Excluir</button>
            </td>
        </tr>`).join('');
}

function changePage(delta) {
    const next = currentPage + delta;
    if (next >= 0 && next < totalPages) loadVeiculos(next);
}

async function loadModelosFiltro() {
    const marcaId = document.getElementById('f-marca').value;
    const sel = document.getElementById('f-modelo');
    sel.innerHTML = '<option value="">Todos</option>';
    if (!marcaId) return;
    const data = await apiFetch(`/modelos/marca/${marcaId}`);
    data.forEach(m => {
        const opt = document.createElement('option');
        opt.value = m.id;
        opt.textContent = m.nome;
        sel.appendChild(opt);
    });
}

async function loadModelosForm() {
    const marcaId = document.getElementById('v-marca').value;
    const sel = document.getElementById('v-modelo');
    sel.innerHTML = '<option value="">Selecione...</option>';
    if (!marcaId) return;
    const data = await apiFetch(`/modelos/marca/${marcaId}`);
    data.forEach(m => {
        const opt = document.createElement('option');
        opt.value = m.id;
        opt.textContent = m.nome;
        sel.appendChild(opt);
    });
}

let veiculosCache = {};

async function openVeiculoModal(id = null) {
    // Garante marcas carregadas
    if (marcasCache.length === 0) await loadMarcas();
    else populateMarcaSelects(marcasCache);

    document.getElementById('v-id').value     = id || '';
    document.getElementById('v-placa').value  = '';
    document.getElementById('v-chassi').value = '';
    document.getElementById('v-cor').value    = '';
    document.getElementById('v-ano').value    = '';
    document.getElementById('v-preco').value  = '';
    document.getElementById('v-km').value     = '';
    document.getElementById('v-status').value = 'DISPONIVEL';
    document.getElementById('v-marca').value  = '';
    document.getElementById('v-modelo').innerHTML = '<option value="">Selecione...</option>';
    document.getElementById('modal-veiculo-title').textContent = id ? 'Editar Veículo' : 'Novo Veículo';

    if (id) {
        try {
            const v = await apiFetch(`/veiculos/${id}`);
            document.getElementById('v-placa').value  = v.placa;
            document.getElementById('v-chassi').value = v.chassi;
            document.getElementById('v-cor').value    = v.cor;
            document.getElementById('v-ano').value    = v.ano;
            document.getElementById('v-preco').value  = v.preco;
            document.getElementById('v-km').value     = v.quilometragem;
            document.getElementById('v-status').value = v.status;
            document.getElementById('v-marca').value  = v.marcaId;
            await loadModelosForm();
            document.getElementById('v-modelo').value = v.modeloId;
        } catch (e) { showToast(e.message, 'error'); return; }
    }

    document.getElementById('modal-veiculo').classList.add('open');
}

async function saveVeiculo() {
    const id   = document.getElementById('v-id').value;
    const body = {
        cor:           document.getElementById('v-cor').value.trim(),
        ano:           parseInt(document.getElementById('v-ano').value),
        preco:         parseFloat(document.getElementById('v-preco').value),
        quilometragem: parseInt(document.getElementById('v-km').value),
        status:        document.getElementById('v-status').value,
        placa:         document.getElementById('v-placa').value.trim(),
        chassi:        document.getElementById('v-chassi').value.trim(),
        modeloId:      parseInt(document.getElementById('v-modelo').value)
    };

    if (!body.cor || !body.ano || !body.preco || body.quilometragem === undefined
        || !body.placa || !body.chassi || !body.modeloId) {
        showToast('Preencha todos os campos obrigatórios.', 'error');
        return;
    }

    try {
        if (id) {
            await apiFetch(`/veiculos/${id}`, { method: 'PUT', body: JSON.stringify(body) });
            showToast('Veículo atualizado.', 'success');
        } else {
            await apiFetch('/veiculos', { method: 'POST', body: JSON.stringify(body) });
            showToast('Veículo cadastrado.', 'success');
        }
        closeModal('modal-veiculo');
        loadVeiculos(currentPage);
    } catch (e) { showToast(e.message, 'error'); }
}

async function deleteVeiculo(id) {
    if (!confirm('Remover este veículo?')) return;
    try {
        await apiFetch(`/veiculos/${id}`, { method: 'DELETE' });
        showToast('Veículo removido.', 'success');
        loadVeiculos(currentPage);
    } catch (e) { showToast(e.message, 'error'); }
}

// ══════════════════════════════════════════════════════════════════════════════
// Init
// ══════════════════════════════════════════════════════════════════════════════
(async function init() {
    await loadMarcas();
    await loadVeiculos(0);
})();
