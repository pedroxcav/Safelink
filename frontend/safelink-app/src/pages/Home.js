function Home() {
    return (
        <section className="hero hero--bleed">
            <div className="hero-card">
            <img className="hero-media" src="https://i.ibb.co/9PGkHxk/cover.webp" alt="SafeLink — segurança contra golpes online" />
            <div className="hero-content">
                <h1 className="hero-title">Fique seguro em cada clique
                </h1>
                <div className="hero-bottom">
                <p className="hero-sub">Verifique ações suspeitas e proteja suas informações online.</p>
                <div className="hero-actions">
                    <a className="btn" href="/menu">Verificar link</a>
                    <a className="btn secondary" href="/report">Relatar golpe</a>
                </div>
                </div>
            </div>
            </div>
        </section>
    );
}

export default Home;