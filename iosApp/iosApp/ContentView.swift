import UIKit
import SwiftUI
import ComposeApp

// MARK: - Auth Views
struct LoginView: UIViewControllerRepresentable {
    let onNavigateToRegister: () -> Void
    let onLoginSuccess: () -> Void
    let onSkip: (UserType) -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.LoginViewController(
            onNavigateToRegister: onNavigateToRegister,
            onLoginSuccess: onLoginSuccess,
            onSkip: onSkip
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct RegisterView: UIViewControllerRepresentable {
    let onNavigateToLogin: () -> Void
    let onRegisterSuccess: () -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.RegisterViewController(
            onNavigateToLogin: onNavigateToLogin,
            onRegisterSuccess: onRegisterSuccess
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

// MARK: - Main App Views
struct HomeView: UIViewControllerRepresentable {
    let viewModel: HomeViewModel

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.HomeViewController(viewModel: viewModel)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct SearchView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.SearchViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct AddEventView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.AddEventViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct SettingsView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.SettingsViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

// MARK: - Home View Model Wrapper
class HomeViewModelWrapper: ObservableObject {
    let viewModel = HomeViewModel()
    @Published var showMap: Bool = true

    func toggle() {
        viewModel.toggleView()
        showMap.toggle()
    }
}

// MARK: - Auth View Model Wrapper
class AuthViewModelWrapper: ObservableObject {
    let viewModel = AuthViewModel()
    @Published var isAuthenticated: Bool = false
    @Published var userType: UserType? = nil

    init() {
        // Observe changes from Kotlin Flow
        // This is simplified - in production you'd use proper Flow observation
    }

    func login(type: UserType) {
        viewModel.login(userType: type)
        isAuthenticated = true
        userType = type
    }

    func skip(type: UserType) {
        viewModel.skip(userType: type)
        isAuthenticated = true
        userType = type
    }

    func register(type: UserType) {
        viewModel.register(userType: type)
        isAuthenticated = true
        userType = type
    }
}

// MARK: - Main Content View
struct ContentView: View {
    @State private var showingRegister = false
    @StateObject var authWrapper = AuthViewModelWrapper()
    @StateObject var homeWrapper = HomeViewModelWrapper()

    var body: some View {
        if authWrapper.isAuthenticated {
            // Show main app with TabView
            TabView {
                // Tab Inicio (siempre presente)
                NavigationStack {
                    HomeView(viewModel: homeWrapper.viewModel)
                        .ignoresSafeArea(edges: .all)
                        .navigationTitle("")
                        .navigationBarTitleDisplayMode(.inline)
                        .toolbar {
                            ToolbarItem(placement: .navigationBarTrailing) {
                                Button(action: {
                                    homeWrapper.toggle()
                                }) {
                                    Image(systemName: homeWrapper.showMap ? "list.bullet" : "map")
                                }
                            }
                        }
                }
                .tabItem {
                    Label("Inicio", systemImage: "house")
                }

                // Tab del medio - cambia según tipo de usuario
                if authWrapper.userType == UserType.business {
                    // Para negocios: pantalla de agregar evento
                    NavigationStack {
                        AddEventView()
                    }
                    .tabItem {
                        Label("Agregar", systemImage: "plus.circle.fill")
                    }
                } else {
                    // Para clientes: pantalla de buscar
                    NavigationStack {
                        SearchView()
                    }
                    .tabItem {
                        Label("Buscar", systemImage: "magnifyingglass")
                    }
                }

                // Tab Ajustes (siempre presente)
                NavigationStack {
                    SettingsView()
                }
                .tabItem {
                    Label("Ajustes", systemImage: "gear")
                }
            }
        } else {
            // Show authentication flow
            if showingRegister {
                RegisterView(
                    onNavigateToLogin: {
                        showingRegister = false
                    },
                    onRegisterSuccess: {
                        authWrapper.register(type: UserType.client)
                    }
                )
                .ignoresSafeArea()
            } else {
                LoginView(
                    onNavigateToRegister: {
                        showingRegister = true
                    },
                    onLoginSuccess: {
                        authWrapper.login(type: UserType.client)
                    },
                    onSkip: { userType in
                        authWrapper.skip(type: userType)
                    }
                )
                .ignoresSafeArea()
            }
        }
    }
}
