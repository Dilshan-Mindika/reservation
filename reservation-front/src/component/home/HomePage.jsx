import React, { useState } from "react";
import LabResult from "../common/LabResult";
import LabSearch from "../common/LabSearch";

const HomePage = () => {

    const [labSearchResults, setLabSearchResults] = useState([]);

    // Function to handle search results
    const handleSearchResult = (results) => {
        setLabSearchResults(results);
    };

    return (
        <div className="home">
            {/* HEADER / BANNER SECTION */}
            <section>
                <header className="header-banner">
                    <div className="overlay"></div>
                    <div className="animated-texts overlay-content">
                        <h1>
                            Welcome to <span className="ulms-color">ULMS</span>
                        </h1><br />
                        <h3>Step into a simple & fast way to reserve a Lab</h3>
                    </div>
                </header>
            </section>

            {/* SEARCH/FIND AVAILABLE LAB SECTION */}
            <LabSearch handleSearchResult={handleSearchResult} />
            <LabResult labSearchResults={labSearchResults} />

            <h4><a className="view-labs-home" href="/labs">All Labs</a></h4>

            <h2 className="home-services">Services at <span className="ulms-color">ULMS</span></h2>

            {/* SERVICES SECTION */}
            <section className="service-section">
                <div className="service-card">
                    <img src="./assets/images/computer-lab.png" alt="Computer Lab" />
                    <div className="service-details">
                        <h3 className="service-title">Computer Labs</h3>
                        <p className="service-description">Fully equipped computer labs with the latest hardware and software for all your needs.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/tech-support.png" alt="Tech Support" />
                    <div className="service-details">
                        <h3 className="service-title">Technical Support</h3>
                        <p className="service-description">24/7 technical support to assist you with any issues or queries during your lab sessions.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/projector.png" alt="Projector" />
                    <div className="service-details">
                        <h3 className="service-title">Smart Board and Audio-Visual Equipment</h3>
                        <p className="service-description">State-of-the-art Smart board and audio-visual equipment available in every lab.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/high-speed-wifi.png" alt="High-Speed WiFi" />
                    <div className="service-details">
                        <h3 className="service-title">High-Speed WiFi</h3>
                        <p className="service-description">Stay connected with our high-speed WiFi, accessible throughout all lab facilities.</p>
                    </div>
                </div>
            </section>

            {/* AVAILABLE LABS SECTION */}
            <section>
                <h2 className="available-labs-title">Available Labs</h2>
                <LabResult labSearchResults={labSearchResults} />
            </section>
        </div>
    );
}

export default HomePage;
